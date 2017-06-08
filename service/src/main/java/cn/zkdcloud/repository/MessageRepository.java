package cn.zkdcloud.repository;

import cn.zkdcloud.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author zk
 * @Date 2017/5/17.
 * @Email 2117251154@qq.com
 */
public interface MessageRepository extends JpaRepository<Message,String> {

    /**给指定消息设置成用户已读
     *
     * @param mid
     */
    @Modifying
    @Query("update Message m set m.messageStatus = true where m.mid = :mid")
    void isRead(@Param("mid")String mid);

    Integer countByMessageStatusFalseAndUid(String uid);

    Page<Message> findByUid(@Param("uid") String uid,Pageable pageable);
}
