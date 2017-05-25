package cn.zkdcloud.processing;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.entity.Function;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.entity.User;
import cn.zkdcloud.interceptors.DispatcherPowerInterceptor;
import cn.zkdcloud.interceptors.InputBaseUserInterceptor;
import cn.zkdcloud.interceptors.LoginCheckInterceptor;
import cn.zkdcloud.interceptors.PowerCheckInterceptor;
import cn.zkdcloud.service.MenuService;
import cn.zkdcloud.service.RoleService;
import cn.zkdcloud.service.UserService;
import cn.zkdcloud.util.Const;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zk
 * @Date 2017/5/20.
 * @Email 2117251154@qq.com
 */
@Controller
@RequestMapping("/user")
public class UserController extends ContentController{
    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    /** 添加用户页面
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addUserPage(ModelMap modelMap){
        Role role = roleService.getRole(getLoginUser().getRoleId());
        modelMap.put("roleList",roleService.listRoleLess(role.getRolePowerSize())); //获取权限比自己小的角色列表
        return "user/add";
    }

    /** 添加用户请求
     *
     * @return
     */
    @Before({InputBaseUserInterceptor.class, PowerCheckInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addUser(){
        userService.addUser(getReqString("username"),getReqString("password"),getReqString("roleId"),getLoginUserName());
        return OPERSTOR_SUCCESS;
    }

    /** 删除用户请求
     *
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String removeUser(){
        userService.removeUser(getReqString("uid"));
        return OPERSTOR_SUCCESS;
    }

    /**修改指定用户页面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/modify",method = RequestMethod.GET)
    public String modifyUserPage(ModelMap modelMap){
        User modifyUser = userService.getUserByUid(getReqString("uid"));
        modelMap.put("modifyUser",modifyUser);
        return "user/modify";
    }

    /** 登录请求
     *
     * @return
     */
    @Before(InputBaseUserInterceptor.class)
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(){
        User user = userService.loginUser(getReqString("username"),getReqString("password"));
        session.setAttribute(Const.USER_LOGIN,user);
        return "登录成功";
    }

    /**注销用户
     *
     * @return
     */
    @RequestMapping(value = "/off",method = RequestMethod.GET)
    public String off(){
        session.removeAttribute(Const.USER_LOGIN);
        return "login";
    }


}
