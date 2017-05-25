package cn.zkdcloud.service;

import cn.zkdcloud.entity.RoleFunction;
import cn.zkdcloud.repository.RoleFunctionRepository;
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
    RoleFunctionRepository roleFunctionRepository;

    public void addRolePower(String roldId,String[] functionIds){

    }

    /** 检查是否有权限
     *
     * @param roleId
     * @param functionId
     * @return
     */
    public boolean findByRoleIdAndFunctionId(String roleId,String functionId){
        List<RoleFunction> rolePowerList = roleFunctionRepository.findByRoleIdAndFunctionId(roleId,functionId);
        if(rolePowerList == null || rolePowerList.isEmpty())
            return false;
        else
            return true;
    }
}
