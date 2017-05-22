package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author zk
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
public interface RoleRepository extends JpaRepository<Role,String>{
}
