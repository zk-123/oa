package cn.zkdcloud.repository;

import cn.zkdcloud.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author zk
 * @Date 2017/5/26.
 * @Email 2117251154@qq.com
 */
public interface RoleUserRepository extends JpaRepository<RoleUser, String> {
    RoleUser findByUid(String uid);
    RoleUser findByRoleId(String roleId);
    RoleUser findByRoleIdAndUid(@Param("roleId")String roleId,@Param("uid")String uid);

    @Modifying
    @Query("update RoleUser ru set ru.roleId = :roleId where ru.uid = :uid")
    void modifyByUid(@Param("uid")String uid,@Param("roleId")String roleId);
}
