package cn.zkdcloud.repository;

import cn.zkdcloud.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/17.
 * @Email 2117251154@qq.com
 */
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    /** 获取权限比自己小或处于游离的人员
     *
     * @param rolePowerSize
     * @param pageable
     * @return
     */
    @Query("from User u where u.uid in(select ru.uid from RoleUser ru where ru.roleId in (" +
            "select r.roleId from Role r where r.rolePowerSize > :rolePowerSize)) " +
            "or uid not in(select ru.uid from RoleUser ru)")
    Page<User> getListUserByRoleLess(@Param("rolePowerSize") Integer rolePowerSize, Pageable pageable);

    @Modifying
    @Query("update User u set u.username = :username, u.password = :password where u.uid = :uid")
    void  modifyBaseUser(@Param("username")String username,@Param("password")String password,@Param("uid")String uid);

    @Modifying
    @Query("update User u set u.username = :username, u.password = :password , u.email = :email, u.url = :url " +
            "where u.uid = :uid")
    void modifyUser(@Param("username")String username,@Param("password")String password,@Param("email")String email,
                    @Param("url")String url,@Param("uid")String uid);
}
