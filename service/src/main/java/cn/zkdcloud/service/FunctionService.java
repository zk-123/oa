package cn.zkdcloud.service;

import cn.zkdcloud.entity.*;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
    RoleFunctionRepository roleFunctionRepository;

    @Autowired
    MenuFunctionRepository menuFunctionRepository;

    @Autowired
    MenuService menuService;

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
     * @param functionName
     * @param functionUrl
     * @param functionDescribe
     * @param functionSort
     */
    public void addFunction(String functionName, String functionUrl,
                            String functionDescribe, Integer functionSort, User user){
        try {
            Function function = new Function();
            function.setFunctionName(functionName);
            function.setFunctionUrl(functionUrl);
            function.setFunctionDescribe(functionDescribe);
            function.setFunctionSort(functionSort);

            Function new_function = functionRepository.save(function);

            Role role = roleRepository.findOne(user.getRoleId());
            disGreeterRoleFunction(new_function.getFunctionId(),role.getRolePowerSize());// 为更高权限的角色分配此功能

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
        List<Role> roleList = roleRepository.listRoleGreaterOrEqual(rolePowerSize);
        for(Role role : roleList){
            RoleFunction rolePower = new RoleFunction();
            rolePower.setRoleId(role.getRoleId());
            rolePower.setFunctionId(functionId);
            roleFunctionRepository.save(rolePower);
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
        Role role = roleRepository.findOne(user.getRoleId());
        List<Function> functionList = new ArrayList<Function>();
        functionList.addAll(role.getFunctionSet());
        return functionList;
    }

    /**带分页的功能列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Page<Function> functionPage(Integer page,Integer pageSize,User user){

        Pageable pageable = new PageRequest(page-1,pageSize,new Sort(Sort.Direction.ASC,"functionSort"));
        List<String> functionIds = new ArrayList<>();

        for(Function function : roleRepository.getOne(user.getRoleId()).getFunctionSet()) //获取对应权限角色的ids
            functionIds.add(function.getFunctionId());
        return functionRepository.findByFunctionIdIn(functionIds,pageable);
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

    /**将功能分配到指定页面的操作
     *
     * @param menuId
     * @param functionId
     * @param username
     */
    public void dispatcherFunction(String menuId,String functionId,String username){
        Menu menu = menuService.getMenu(menuId);
        Function function = functionRepository.getOne(functionId);

        if(menu == null || function == null)
            throw new TipException("目录或指定功能不能为空");

        MenuFunction menuFunction = new MenuFunction();
        menuFunction.setFunctionId(functionId);
        menuFunction.setMenuId(menuId);
        menuFunctionRepository.save(menuFunction);

        recordLog(username+"将"+function.getFunctionName()+"功能分配到"+menu.getMenuName()+"目录下");
    }

}
