package cn.zkdcloud.service;

import cn.zkdcloud.entity.Process;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.ProcessRepository;
import cn.zkdcloud.repository.RoleRepository;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Author zk
 * @Date 2017/5/28.
 * @Email 2117251154@qq.com
 */
@Service
@Transactional
public class ProcessService {
    public static Logger logger = Logger.getLogger(ProcessService.class);

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    RoleRepository roleRepository;

    /**添加一个审批流程
     *
     * @param processName
     * @param processDescribe
     * @param processUrl
     * @param processRoles
     */
    public void add(String processName,String processDescribe,String processUrl,String[] processRoles,String username){
        JSONObject rolesJson = new JSONObject();
        for(int i = 0 ; i < processRoles.length; i++)
            rolesJson.put(i+1,processRoles[i]);

        Process process = new Process();
        process.setProcessName(processName);
        process.setProcessDescribe(processDescribe);
        process.setProcessUrl(processUrl);
        process.setProcessRoles(rolesJson.toString());

        processRepository.save(process);
        logger.info(username+"添加审批流程"+processName);
    }

    /** 修改指定审批流程
     *
     * @param processId
     * @param processName
     * @param processDescribe
     * @param processUrl
     * @param processRoles
     * @param username
     */
    public void modify(String processId,String processName,String processDescribe,String processUrl,
                       String[] processRoles,String username){
        JSONObject rolesJson = new JSONObject();
        for(int i = 0 ; i < processRoles.length; i++)
            rolesJson.put(i+1,processRoles[i]);
        System.out.println(rolesJson.toString());
        processRepository.modifyProcess(processName,processDescribe,processUrl,rolesJson.toString(),processId);
        logger.info(username+"修改审批流程"+processName);
    }

    /**删除指定的审批流程
     *
     * @param processId
     */
    public void delete(String processId,String username){
        Process process = processRepository.getOne(processId);
        if(process == null)
            throw new TipException("不存在该审批流");
        logger.info(username+"删除审批流"+process.getProcessName());
        processRepository.delete(processId);
    }

    /** 获取审批流listPage
     *
     * @return
     */
    public Page<Process> getProcessListPage(Integer curPage,Integer pageSize){
        Pageable pageable = new PageRequest(curPage-1,pageSize);
        return processRepository.findAll(pageable);
    }

    /** 获取审批流程list
     *
     * @return
     */
    public List<Process> getProcessList(){
        return processRepository.findAll();
    }
    /** 获取指定的审批流
     *
     * @param processId
     * @return
     */
    public Process getByProcessId(String processId){
        Process process = processRepository.getOne(processId);
        process.setRoleList(rolesJsonToList(JSONObject.fromObject(process.getProcessRoles())));
        if(process == null)
            throw new TipException("不存在该审批流");
        return process;
    }

    /** json中的roleIds转化成list(Role)格式
     *
     * @param rolesJson
     * @return
     */
    public List<Role> rolesJsonToList(JSONObject rolesJson){
        LinkedList<Role> linkedList = new LinkedList<>();
        Set<String> keySet = rolesJson.keySet();
        for(String i :keySet){
            String roleId = rolesJson.getString(i);
            Role role = roleRepository.findOne(roleId);
            if(role != null)
                linkedList.add(role);
        }
        return linkedList;
    }
}
