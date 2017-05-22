package cn.zkdcloud.service;

import cn.zkdcloud.entity.OperatorLog;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.OperatorLogRepository;
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

    /** 记录操作
     *
     * @param content
     */
    public void recordLog(String content){
        OperatorLog log = new OperatorLog();
        log.setOperatorContent(content);
        log.setOpreratorCreateDate(new Date());
        log.setOperatorType("角色");
        operatorLogRepository.save(log);

        logger.info(content);
    }

    /** 添加角色
     *
     * @param roleName
     * @param roleDescribe
     */
    public void addRole(String roleName,String roleDescribe,String username){
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDescribe(roleDescribe);
        role.setRoleDate(new Date());

        roleRepository.save(role);
        recordLog(username+"添加角色：" + roleName);
    }

    /**获取角色
     *
     * @param roleId
     */
    public Role getRole(String roleId){
        Role role = roleRepository.findOne(roleId);
        if(role == null)
            throw new TipException("不存在该用户");
        return role;
    }

    /**删除角色
     *
     * @param roleId
     * @param username
     */
    public void removeRole(String roleId,String username){
        try {
            roleRepository.delete(roleId);
        } catch (Exception e) {
            throw new TipException("无此角色");
        }
    }

    /**修改角色信息
     *
     * @param roleId
     * @param roleName
     * @param roleDescribe
     */
    public void modifyRole(String roleId,String roleName,String roleDescribe,String username){
        Role oldRole = getRole(roleId);

        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setRoleDescribe(roleDescribe);
        role.setRoleDate(new Date());
        roleRepository.save(role);

        recordLog(username + "修改角色信息: old: "+ oldRole + ", new:" +role);
    }

    /** 角色分页列表
     *
     * @return
     */
    public Page<Role> listAllRolePage(Integer page,Integer pageSize){
        Pageable pageable = new PageRequest(page-1,pageSize,new Sort(Sort.Direction.ASC,"roleDate"));
        return roleRepository.findAll(pageable);
    }

    /** 角色列表
     *
     * @return
     */
    public List<Role> listRole(){
        return roleRepository.findAll();
    }

}
