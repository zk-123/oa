package cn.zkdcloud.service;

import cn.zkdcloud.entity.Flow;
import cn.zkdcloud.entity.FlowStep;
import cn.zkdcloud.entity.Process;
import cn.zkdcloud.entity.Role;
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

    /**添加审批申请
     *
     * @param uid
     * @param processId
     * @param flowUrl
     */
    public void add(String uid,String processId,String flowUrl){
        Flow new_flow = new Flow();
        new_flow.setFlowUrl(flowUrl);
        new_flow.setProcessId(processId);
        new_flow.setUid(uid);
        new_flow.setFlowDate(new Date());
        Flow flow = flowRepository.save(new_flow);//保存请求审批

        Process process = processService.getByProcessId(processId); //保存其审批流程
        List<Role> roleList = process.getRoleList();
        for(int i = 0; i < roleList.size();i++){
            FlowStep flowStep = new FlowStep();
            flowStep.setFlowId(flow.getFlowId());
            flowStep.setStep(i+1);
            flowStep.setRoleId(roleList.get(i).getRoleId());
            if(i == 0) //第一个等待审批
                flowStep.setAccept(true);
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
}
