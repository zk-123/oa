package cn.zkdcloud.service;



import cn.zkdcloud.entity.OperatorLog;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.entity.RoleUser;
import cn.zkdcloud.entity.User;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.repository.OperatorLogRepository;
import cn.zkdcloud.repository.RoleUserRepository;
import cn.zkdcloud.repository.UserRepository;
import cn.zkdcloud.util.Md5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


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

    @Autowired
    RoleUserRepository roleUserRepository;

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
    public void addUser(String username,String password,String roleId,String operatorUserName){
        User user = new User();
        user.setUsername(username);
        user.setPassword(Md5Util.toMD5(password));

        User new_user = userRespository.save(user);
        if(new_user == null)
            throw new TipException("添加失败");

        if(roleId != null){ //给用户分配角色，允许为空
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUid(new_user.getUid());
            roleUserRepository.save(roleUser);
        }
        recordLog(operatorUserName+"添加用户："+username);
    }

    /**更新角色
     *
     * @param uid
     * @param username
     * @param password
     * @param roleId
     * @param operatorUserName
     */
    public void modifyUser(String uid,String username,String password,String roleId,String operatorUserName){
        User user = userRespository.getOne(uid);
        password = (password == null || password.equals("")) ? user.getPassword() : Md5Util.toMD5(password);//若不设密码，则为原密码

        userRespository.modifyBaseUser(username,password,uid);

        if(roleId != null){ //不为空，分配角色
            RoleUser roleUser = roleUserRepository.findByRoleIdAndUid(roleId,uid); //存在该角色，则不分配
            if(roleUser == null){
                RoleUser roleUser1 = roleUserRepository.findByUid(uid);
                if(roleUser1 == null){//不存在，则新建
                    RoleUser new_roleUser = new RoleUser();
                    new_roleUser.setRoleId(roleId);
                    new_roleUser.setUid(uid);
                    roleUserRepository.save(new_roleUser);
                } else { //存在则更新
                    roleUserRepository.modifyByUid(uid,roleId);
                }
            }
        }
        recordLog(operatorUserName+"更新用户"+username);
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

    /** 获取权限比自己小或游离状态的用户
     *
     * @param rolePowerSize
     * @return
     */
    public Page<User> getUserListByRoleLess(Integer page,Integer pageSize, Integer rolePowerSize){
        if( rolePowerSize == null)
            throw new TipException("无此权限");
        Pageable pageable = new PageRequest(page-1,pageSize);
        return userRespository.getListUserByRoleLess(rolePowerSize,pageable);
    }
}
