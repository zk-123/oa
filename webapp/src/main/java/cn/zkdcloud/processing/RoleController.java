package cn.zkdcloud.processing;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.interceptors.InputRoleInterceptor;
import cn.zkdcloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zk
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
@Controller
@RequestMapping("/role")
public class RoleController extends ContentController{

    @Autowired
    RoleService roleService;

    /** 添加角色页面
     *
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addRolePage(){
        return "role/add";
    }

    /** 添加角色
     *
     * @return
     */
    @Before(InputRoleInterceptor.class)
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addRole(){
        roleService.addRole(getReqString("roleName"),getReqString("roleDescribe"),Integer.parseInt(getReqString("rolePowerSize")),"haha");
        return OPERSTOR_SUCCESS;
    }

    @RequestMapping(value = "/modify",method = RequestMethod.GET)
    public String modifyPage(ModelMap modelMap){
        modelMap.put("modifyRole",roleService.getRole(getReqString("roleId")));
        return "role/modify";
    }
    /** 修改角色操作
     *
     * @return
     */
    @Before(InputRoleInterceptor.class)
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public String modifyRole(){
        roleService.modifyRole(getReqString("roleId"),getReqString("roleName"),getReqString("roleDescribe"),"haha");
        return OPERSTOR_SUCCESS;
    }

    /** 角色列表
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String listRole(ModelMap modelMap){
        Integer curPage = getReqString("p") == null ? 1 : Integer.parseInt(getReqString("p"));
        Integer pageSize = getReqString("pageSize") == null ? 10 : Integer.parseInt(getReqString("pageSize"));

        Page<Role> rolePage = roleService.listAllRolePage(curPage,pageSize);
        Integer sumPage = rolePage.getTotalPages();
        modelMap.put("sumPage",sumPage);
        modelMap.put("curPage",curPage);
        modelMap.put("rolePage",rolePage);
        return "role/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRole(){
        roleService.removeRole(getReqString("roleId"),"haha");
        return OPERSTOR_SUCCESS;
    }
}
