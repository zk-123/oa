package cn.zkdcloud.repository;

import cn.zkdcloud.entity.RolePower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
public interface RolePowerRepository extends JpaRepository<RolePower, String> {

    List<RolePower> findByRoleIdAndFunctionId(String roleId,String functionId);

}
