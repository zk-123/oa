package cn.zkdcloud.processing;


import cn.zkdcloud.entity.MenuTree;
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
    @RequestMapping("/")
    public String index(ModelMap map) {
        MenuTree menuTree= menuService.menuTreeAllList();
        map.put("menuTree",menuTree);

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