package cn.zkdcloud.repository;

import cn.zkdcloud.entity.MenuRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @Author zk
 * @Date 2017/5/24.
 * @Email 2117251154@qq.com
 */
public interface MenuRoleRepository extends JpaRepository<MenuRole,String>{

    void deleteByRoleId(String roleId);

    void deleteByMenuIdAndRoleId(@Param("menuId")String menuId,@Param("roleId")String roleId);
}
