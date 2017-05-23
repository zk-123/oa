package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
public interface RoleRepository extends JpaRepository<Role,String>{
    List<Role> findByRolePowerSizeGreaterThan(Integer roleSize);

    @Query("from Role r where r.rolePowerSize <= :roleSize")
    List<Role> morePowerThanThis(@Param("roleSize") Integer roleSize);
}
