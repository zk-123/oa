package cn.zkdcloud.service;

import cn.zkdcloud.entity.*;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.FunctionRepository;
import cn.zkdcloud.repository.OperatorLogRepository;
import cn.zkdcloud.repository.RolePowerRepository;
import cn.zkdcloud.repository.RoleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/19.
 * @Email 2117251154@qq.com
 */
@Service
@Transactional
public class FunctionService {

    private static Logger logger = Logger.getLogger(FunctionService.class);

    @Autowired
    OperatorLogRepository operatorLogRepository;

    @Autowired
    FunctionRepository functionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RolePowerRepository rolePowerRepository;

    /** 记录操作
     *
     * @param content
     */
    public void recordLog(String content){
        OperatorLog log = new OperatorLog();
        log.setOperatorContent(content);
        log.setOpreratorCreateDate(new Date());
        log.setOperatorType("function");
        operatorLogRepository.save(log);

        logger.info(content);
    }

    /** 添加功能
     *
     * @param menuId
     * @param functionName
     * @param functionUrl
     * @param functionDescribe
     * @param functionSort
     */
    public void addFunction(String menuId, String functionName, String functionUrl,
                            String functionDescribe, Integer functionSort, User user){
        try {
            Function function = new Function();
            function.setMenuId(menuId);
            function.setFunctionName(functionName);
            function.setFunctionUrl(functionUrl);
            function.setFunctionDescribe(functionDescribe);
            function.setFunctionSort(functionSort);

            Function new_function = functionRepository.save(function);

            Role role = roleRepository.findOne(user.getRoleId()); // 为更高权限的角色分配此功能
            disGreeterRoleFunction(new_function.getFunctionId(),role.getRolePowerSize());

            recordLog(user.getUsername()+"增加功能"+functionName);
        } catch (Exception e) {
            throw new TipException("输入项有误");
        }
    }

    /** 为更高权限分配该功能
     *
     * @param rolePowerSize
     */
    public void disGreeterRoleFunction(String functionId,Integer rolePowerSize){
        List<Role> roleList = roleRepository.morePowerThanThis(rolePowerSize);
        for(Role role : roleList){
            RolePower rolePower = new RolePower();
            rolePower.setRoleId(role.getRoleId());
            rolePower.setFunctionId(functionId);
            rolePowerRepository.save(rolePower);
        }
    }
    /** 移除功能
     *
     * @param functionId
     * @param username
     */
    public void removeFunction(String functionId,String username){
        Function function = functionRepository.getOne(functionId);
        if(function == null)
            throw new TipException("无此功能");
        functionRepository.delete(functionId);
        recordLog(username+"删除功能"+function.getFunctionName());
    }

    /** 全部功能列表
     *
     * @return
     */
    public List<Function> functionList(User user){
        Sort sort = new Sort(Sort.Direction.ASC,"functionSort");
        return functionRepository.findByRoleRoleId(user.getRoleId(),sort);
    }

    /**带分页的功能列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Page<Function> functionPage(Integer page,Integer pageSize,User user){

        Pageable pageable = new PageRequest(page-1,pageSize,new Sort(Sort.Direction.ASC,"functionSort"));
        return functionRepository.findByRoleRoleId(user.getRoleId(),pageable);
    }

    /** 修改功能
     *
     * @param functionId
     * @param functionName
     * @param functionDescribe
     * @param functionSort
     * @param functionUrl
     * @param menuId
     * @param username
     */
    public void modifyFunction(String functionId,String functionName, String functionDescribe,
                               Integer functionSort, String functionUrl,String menuId,String username){
        Function function = new Function();
        function.setFunctionId(functionId);
        function.setFunctionName(functionName);
        function.setFunctionDescribe(functionDescribe);
        function.setFunctionSort(functionSort);
        function.setFunctionUrl(functionUrl);
        function.setMenuId(menuId);

        Function oldFunciton = functionRepository.findOne(functionId);
        if(oldFunciton == null)
            throw new TipException("没有此功能");
        functionRepository.save(function);

        recordLog(username + "修改功能  原："+ oldFunciton +",先："+function);
    }

    /**根据id查找此功能
     *
     * @param functionId
     * @return
     */
    public Function findFunctionById(String functionId){
        Function function = functionRepository.getOne(functionId);
        if(function == null)
            throw new TipException("无此功能");
        return function;
    }

    /** 根据功能的url查找
     *
     * @param url
     * @return
     */
    public Function findFunctionByUrl(String url){
        List<Function> functions = functionRepository.findByFunctionUrl(url);
        if(functions == null || functions.isEmpty())
            throw new TipException("无此功能");
        return functions.get(0);
    }
}
