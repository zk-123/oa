package cn.zkdcloud.repository;

import cn.zkdcloud.entity.RoleFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
public interface RoleFunctionRepository extends JpaRepository<RoleFunction, String> {

    List<RoleFunction> findByRoleIdAndFunctionId(String roleId, String functionId);
    void deleteByRoleId(String roldId);

    void deleteByRoleIdAndFunctionId(@Param("roleId")String roleId,@Param("functionId")String functionId);
}
