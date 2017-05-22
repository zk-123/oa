package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author zk
 * @Date 2017/5/17.
 * @Email 2117251154@qq.com
 */
public interface MessageRepository extends JpaRepository<Message,String> {
}
