package cn.zkdcloud.service;

import cn.zkdcloud.entity.*;
import cn.zkdcloud.entity.Process;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.FlowRepository;
import cn.zkdcloud.repository.FlowStepRepository;
import cn.zkdcloud.repository.ProcessRepository;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Author zk
 * @Date 2017/5/28.
 * @Email 2117251154@qq.com
 */
@Service
@Transactional
public class FlowService {

    private static Logger logger = Logger.getLogger(FunctionService.class); //不再在数据中插入日志，日志统一放到日志文件中

    @Autowired
    private FlowRepository flowRepository;

    @Autowired
    private FlowStepRepository flowStepRepository;

    @Autowired
    private ProcessService processService;

    @Autowired
    private MessageService messageService;

    /**添加审批申请
     *
     * @param user
     * @param processId
     * @param flowUrl
     */
    public void add(User user, String processId, String flowUrl){
        Flow new_flow = new Flow();
        new_flow.setFlowUrl(flowUrl);
        new_flow.setProcessId(processId);
        new_flow.setUid(user.getUid());
        new_flow.setFlowDate(new Date());
        Flow flow = flowRepository.save(new_flow);//保存请求审批

        Process process = processService.getByProcessId(processId); //保存其审批流程
        List<Role> roleList = process.getRoleList();
        for(int i = 0; i < roleList.size();i++){
            FlowStep flowStep = new FlowStep();
            flowStep.setFlowId(flow.getFlowId());
            flowStep.setStep(i+1);
            flowStep.setRoleId(roleList.get(i).getRoleId());
            if(i == 0) {//第一个等待审批,并发消息提醒
                flowStep.setAccept(true);
                messageService.sendMessageToRole(flowStep.getRoleId(),"您有一个新的审批，请及时处理",
                        user.getUsername()+"提交了"+process.getProcessName()+"，请及时审批该申请");
            }

            flowStepRepository.save(flowStep);
        }

    }

    /**获取指定用户流程页面的分页列表
     *
     * @param curPage
     * @param pageSize
     * @return
     */
    public Page<Flow> flowPageListByUid(String uid,Integer curPage, Integer pageSize){
        Pageable pageable = new PageRequest(curPage-1,pageSize);
        return flowRepository.findByUid(uid,pageable);
    }

    /** 根据指定角色，获取其要审批的列表
     *
     * @param curPage
     * @param pageSize
     * @param roleId
     * @return
     */
    public Page<FlowStep> flowStepPageByRoleId(Integer curPage,Integer pageSize,String roleId){
        Pageable pageable = new PageRequest(curPage-1,pageSize);
        return flowStepRepository.findByStatusFalseAndAcceptTrueAndRoleId(roleId,pageable);
    }

    /** 检查该角色是否能审批
     *
     * @param roleId
     * @param flowStepId
     * @return
     */
    public boolean isApprove(String roleId,String flowStepId){
        FlowStep flowStep = flowStepRepository.findOne(flowStepId);
        if( !flowStep.isAccept() || flowStep.isStatus() || !flowStep.getRoleId().equals(roleId))
            throw new TipException("该申请目前不该由你审批");
        return true;
    }

    /** 审批指定的申请
     *
     * @param flowStepId
     * @param remarks
     * @param accept
     */
    public void approve(String flowStepId,String remarks,boolean accept,String username){
        FlowStep flowStep = flowStepRepository.findOne(flowStepId);
        flowStepRepository.modifyApproveByFlowStepId(flowStepId,accept,remarks,username,new Date());//审批操作
        messageService.add(flowStep.getFlow().getUid(),"申请的审批有新的进度",
                username+"审批了你的申请，点击<a href='../flow/detail?flowId="+flowStep.getFlow().getFlowId()+"'>[这里查看进度]</a>"); //提醒申请者新的进度
        if(accept){
            Iterator<FlowStep> flowStepIterator = flowStep.getFlow().getFlowStepSet().iterator(); //将下一个步骤设为准备,做到这里，我已经后悔应该审批后再插入下一个步骤
            while(flowStepIterator.hasNext()){
                FlowStep nextFlowStep = flowStepIterator.next();
                if(nextFlowStep.getFlowStepId().equals(flowStepId)){
                    if(flowStepIterator.hasNext()) {
                        FlowStep newFlowStep = flowStepIterator.next();
                        messageService.sendMessageToRole(newFlowStep.getRoleId(),"您有一个新的审批，请及时处理",
                                newFlowStep.getFlow().getUser().getUsername()+"提交了"+newFlowStep.getFlow().getProcess().getProcessName()+"，请及时审批该申请");
                        flowStepRepository.modifyAcceptById(newFlowStep.getFlowStepId());
                    }
                    break;
                }
            }
        }


        logger.info(username + "审批了"+ flowStep.getFlow().getUser().getUsername()+ "提交的" +flowStep.getFlow().getProcess().getProcessName());
    }

    /**根据id查找指定的用户申请
     *
     * @param flowId
     * @return
     */
    public Flow getFlowById(String flowId){
        Flow flow = flowRepository.findOne(flowId);
        if(flow == null)
            throw new TipException("找不到该流程");
        return flow;
    }
}
