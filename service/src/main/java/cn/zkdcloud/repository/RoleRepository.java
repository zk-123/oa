package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**权限大于自己的角色分页列表
     *
     * @param roleSize
     * @param pageable
     * @return
     */
    Page<Role> findByRolePowerSizeGreaterThan(Integer roleSize, Pageable pageable);

    /**权限小于自己的角色列表
     *
     * @param roleSize
     * @return
     */
    List<Role> findByRolePowerSizeGreaterThan(Integer roleSize);

    /**权限大于或等于自己的角色列表
     *
     * @param roleSize
     * @return
     */
    @Query("from Role r where r.rolePowerSize <= :roleSize")
    List<Role> listRoleGreaterOrEqual(@Param("roleSize") Integer roleSize);
}
