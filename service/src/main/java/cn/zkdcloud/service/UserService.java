package cn.zkdcloud.service;



import cn.zkdcloud.entity.OperatorLog;
import cn.zkdcloud.entity.User;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.OperatorLogRepository;
import cn.zkdcloud.repository.UserRepository;
import cn.zkdcloud.util.Md5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/** 下次做我会把他们抽象成接口
 * @Author zk
 * @Date 2017/5/17.
 * @Email 2117251154@qq.com
 */
@Service
@Transactional
public class UserService {

    private static Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    UserRepository userRespository;

    @Autowired
    OperatorLogRepository operatorLogRepository;

    /** 记录操作
     *
     * @param content
     */
    public void recordLog(String content){
        OperatorLog log = new OperatorLog();
        log.setOperatorContent(content);
        log.setOpreratorCreateDate(new Date());
        log.setOperatorType("人员");
        operatorLogRepository.save(log);

        logger.info(content);
    }

    /**
     *  添加用户
     * @param username
     * @param password
     * @param operatorUserName
     */
    public void addUser(String username,String password,String operatorUserName){
        User user = new User();
        user.setUsername(username);
        user.setPassword(Md5Util.toMD5(password));
        if(userRespository.save(user) == null)
            throw new TipException("添加失败");
        recordLog(operatorUserName+"添加用户："+username);
    }

    /** 登录校验
     *
     * @param username
     * @param password
     * @return
     */
    public User loginUser(String username,String password){
        User user = userRespository.findByUsername(username);
        if(user == null)
            throw new TipException("不存在该用户");
        if(!user.getPassword().equals(Md5Util.toMD5(password)))
           throw new TipException("密码错误");
        return user;
    }

    /** 删除指定人员
     *
     * @param uid
     */
    public void removeUser(String uid){
        try {
            userRespository.delete(uid);
        } catch (Exception e) {
            throw new TipException("无此人");
        }
    }

    /** 获取指定人员
     *
     * @param uid
     */
    public User getUserByUid(String uid){
        if(uid == null)
            throw new TipException("无此人");

        User user = userRespository.findOne(uid);
        if(user == null)
            throw new TipException("无此人");
        return user;
    }
}
