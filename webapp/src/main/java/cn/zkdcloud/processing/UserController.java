package cn.zkdcloud.processing;

import cn.zkdcloud.entity.User;
import cn.zkdcloud.service.MenuService;
import cn.zkdcloud.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zk
 * @Date 2017/5/20.
 * @Email 2117251154@qq.com
 */
@Controller
@RequestMapping("/role")
public class UserController extends ContentController{
    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    MenuService menuService;

    @Autowired
    UserService userService;

    /** 添加用户页面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addRole(ModelMap modelMap){
        modelMap.put("menuTree",menuService.menuTreeAllList());
        return "role/add";
    }

    /** 添加用户请求
     *
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addRole(){
        userService.addUser(getReqString("username"),getReqString("password"),"haha");
        return "操作成功";
    }

    /** 登录请求
     *
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(){
        User user = userService.loginUser(getReqString("username"),getReqString("passsword"));
        session.setAttribute(USER_LOGIN,user);
        return "登录成功";
    }

}
