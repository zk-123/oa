package cn.zkdcloud.service;

import cn.zkdcloud.entity.RolePower;
import cn.zkdcloud.repository.RolePowerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/23.
 * @Email 2117251154@qq.com
 */
@Service
@Transactional
public class RolePowerService {

    private static Logger logger = Logger.getLogger(RoleService.class);

    @Autowired
    RolePowerRepository rolePowerRepository;

    public void addRolePower(String roldId,String[] functionIds){

    }

    /** 检查是否有权限
     *
     * @param roleId
     * @param functionId
     * @return
     */
    public boolean findByRoleIdAndFunctionId(String roleId,String functionId){
        List<RolePower> rolePowerList = rolePowerRepository.findByRoleIdAndFunctionId(roleId,functionId);
        if(rolePowerList == null || rolePowerList.isEmpty())
            return false;
        else
            return true;
    }
}
