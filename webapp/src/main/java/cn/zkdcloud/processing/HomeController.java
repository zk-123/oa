package cn.zkdcloud.processing;


import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.entity.MenuTree;
import cn.zkdcloud.interceptors.LoginCheckInterceptor;
import cn.zkdcloud.service.MenuService;
import cn.zkdcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*** 主页
 * zk
 */
@Controller
public class HomeController extends ContentController{

    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    /** home页面
     *
     * @param map
     * @return
     */
    @Before({LoginCheckInterceptor.class})
    @RequestMapping("/")
    public String index(ModelMap map) {
        return "index";
    }

    /** 登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
}