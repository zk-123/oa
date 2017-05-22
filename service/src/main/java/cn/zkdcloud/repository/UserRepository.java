package cn.zkdcloud.repository;

import cn.zkdcloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
