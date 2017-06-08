package cn.zkdcloud.service;

import cn.zkdcloud.entity.Message;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.entity.RoleUser;
import cn.zkdcloud.repository.MessageRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @Author zk
 * @Date 2017/6/5.
 * @Email 2117251154@qq.com
 */
@Service
@Transactional
public class MessageService {

    private static Logger logger = Logger.getLogger(MenuService.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoleService roleService;

    /**给指定用户添加一条消息
     *
     * @param uid
     * @param title
     * @param content
     */
    public void add(String uid,String title,String content){
        Message message = new Message();
        message.setMessageTitle(title);
        message.setMessageContent(content);
        message.setUid(uid);
        message.setMessageDate(new Date());

        messageRepository.save(message);
    }

    /**给指定消息设置成已读
     *
     * @param mid
     */
    public void isRead(String mid){
        messageRepository.isRead(mid);
    }

    /** 获取消息分页
     *
     * @return
     */
    public Page<Message> getMessagePageList(Integer curPage,Integer pageSize,String uid){
        Pageable pageable = new PageRequest(curPage-1,pageSize,new Sort(Sort.Direction.DESC,"messageDate"));
        return messageRepository.findByUid(uid,pageable);
    }

    /**根据id获取指定的message
     *
     * @param mid
     * @return
     */
    public Message getMessageByMid(String mid){
        return messageRepository.findOne(mid);
    }

    /** 给指定角色的所有用户发信息
     *
     * @param roleId
     */
    public void sendMessageToRole(String roleId,String title,String content){
        Role role = roleService.getRole(roleId);
        for(RoleUser roleUser : role.getRoleUserSet()){
            add(roleUser.getUid(),title,content);
        }
    }

    /**获取指定用户 未读消息条数
     *
     * @param uid
     * @return
     */
    public Integer getUnReadMessageByUid(String uid){
        return messageRepository.countByMessageStatusFalseAndUid(uid);
    }
}
