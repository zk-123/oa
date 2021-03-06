package cn.zkdcloud.service;

import cn.zkdcloud.entity.*;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.MenuRoleRepository;
import cn.zkdcloud.repository.OperatorLogRepository;
import cn.zkdcloud.repository.RoleFunctionRepository;
import cn.zkdcloud.repository.RoleRepository;
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
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
@Service
@Transactional
public class RoleService {
    private static Logger logger = Logger.getLogger(RoleService.class);

    @Autowired
    private OperatorLogRepository operatorLogRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleFunctionRepository roleFunctionRepository;

    @Autowired
    private MenuRoleRepository menuRoleRepository;

    /**
     * 记录操作
     *
     * @param content
     */
    public void recordLog(String content) {
        OperatorLog log = new OperatorLog();
        log.setOperatorContent(content);
        log.setOpreratorCreateDate(new Date());
        log.setOperatorType("角色");
        operatorLogRepository.save(log);

        logger.info(content);
    }

    /**
     * 添加角色
     *
     * @param roleName
     * @param roleDescribe
     */
    public void addRole(String roleName, String roleDescribe,Integer rolePowerSize, String username) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDescribe(roleDescribe);
        role.setRolePowerSize(rolePowerSize);
        role.setRoleDate(new Date());

        roleRepository.save(role);
        recordLog(username + "添加角色：" + roleName);
    }

    /**
     * 获取角色
     *
     * @param roleId
     */
    public Role getRole(String roleId) {
        Role role = roleRepository.findOne(roleId);
        if (role == null)
            throw new TipException("不存在该用户");
        return role;
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @param username
     */
    public void removeRole(String roleId, String username) {
        Role role = roleRepository.findOne(roleId);
        if(role == null)
            throw new TipException("无此角色");

        roleRepository.delete(roleId);
        recordLog(username + "删除角色"+role.getRoleName());
    }

    /**
     * 修改角色信息
     *
     * @param roleId
     * @param roleName
     * @param roleDescribe
     */
    public void modifyRole(String roleId, String roleName, String roleDescribe,Integer rolePowerSize, String username) {
        Role oldRole = getRole(roleId);

        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setRoleDescribe(roleDescribe);
        role.setRoleDate(new Date());
        role.setRolePowerSize(rolePowerSize);
        roleRepository.save(role);

        recordLog(username + "修改角色信息: old: " + oldRole + ", new:" + role);
    }

    /**
     * 角色分页列表
     *
     * @return
     */
    public Page<Role> listRolesPage(Integer page, Integer pageSize,Integer rolePowerSize) {
        Pageable pageable = new PageRequest(page - 1, pageSize, new Sort(Sort.Direction.ASC, "roleDate"));
        return roleRepository.findByRolePowerSizeGreaterThan(rolePowerSize,pageable);
    }

    /**
     * 获取权限小于自己的角色列表
     *
     * @return
     */
    public List<Role> listRoleLess(Integer rolePowerSize) {
        return roleRepository.findByRolePowerSizeGreaterThan(rolePowerSize);
    }

    /** 获取权限大于或自己的角色列表
     *
     * @param rolePowerSize
     * @return
     */
    public List<Role> listRoleGreaterOrEqual(Integer rolePowerSize){
        return roleRepository.listRoleGreaterOrEqual(rolePowerSize);
    }

    /**获取全部角色列表
     *
     * @return
     */
    public List<Role> listRoles(){
        return roleRepository.findAll();
    }
    /** 为指定用户分配权限操作
     *
     * @param roleId
     */
    public void dispatcherRole(String[] functionIdAttr,String[] menuIdAttr,String roleId){
        List<String> functionIds = new ArrayList<>();
        List<String> menuIds = new ArrayList<>();
        if(functionIdAttr != null)
            Collections.addAll(functionIds,functionIdAttr);
        if(menuIdAttr != null)
            Collections.addAll(menuIds,menuIdAttr);

        List<String> oldFunctionIds = new ArrayList<>();
        List<String> oldMenuIds = new ArrayList<>();

        Role role = roleRepository.getOne(roleId);

        for(RoleFunction roleFunction: role.getRoleFunctionSet())
            oldFunctionIds.add(roleFunction.getFunctionId());
        for(MenuRole menuRole : role.getMenuRoleSet())
            oldMenuIds.add(menuRole.getMenuId());

        for(String functionId : oldFunctionIds){ //删除移除的功能
            if(!functionIds.contains(functionId)){
                roleFunctionRepository.deleteByRoleIdAndFunctionId(roleId,functionId);
            }
        }

        for(String functionId : functionIds){  //增加新的功能
            if(!oldFunctionIds.contains(functionId)){
                RoleFunction roleFunction = new RoleFunction();
                roleFunction.setFunctionId(functionId);
                roleFunction.setRoleId(roleId);
                roleFunctionRepository.save(roleFunction);
            }
        }

        for(String menuId : oldMenuIds){ //删除移除的目录
            if(!menuIds.contains(menuId))
                menuRoleRepository.deleteByMenuIdAndRoleId(menuId,roleId);
        }

        for(String menuId : menuIds){   //增加新的目录
            if(!oldMenuIds.contains(menuId)){
                MenuRole menuRole = new MenuRole();
                menuRole.setRoleId(roleId);
                menuRole.setMenuId(menuId);
                menuRoleRepository.save(menuRole);
            }
        }
    }

}
