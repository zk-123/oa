package cn.zkdcloud.processing;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.entity.*;
import cn.zkdcloud.interceptors.*;
import cn.zkdcloud.service.FunctionService;
import cn.zkdcloud.service.MenuService;
import cn.zkdcloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Autowired
    MenuService menuService;

    @Autowired
    FunctionService functionService;

    /** 添加角色页面
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class, PowerCheckInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addRolePage(ModelMap modelMap){
        modelMap.put("myPowerSize",getLoginUser().getRole().getRolePowerSize());
        return "role/add";
    }

    /** 添加角色
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class, PowerCheckInterceptor.class,InputRoleInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addRole(){
        roleService.addRole(getReqString("roleName"),getReqString("roleDescribe"),Integer.parseInt(getReqString("rolePowerSize")),"haha");
        return OPERSTOR_SUCCESS;
    }

    /**修改角色页面
     *
     * @param modelMap
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/modify",method = RequestMethod.GET)
    public String modifyPage(ModelMap modelMap){
        modelMap.put("myPowerSize",getLoginUser().getRole().getRolePowerSize());
        modelMap.put("modifyRole",roleService.getRole(getReqString("roleId")));
        return "role/modify";
    }
    /** 修改角色操作
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class,InputRoleInterceptor.class, ModifyRoleInterceptor.class})
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public String modifyRole(){
        roleService.modifyRole(getReqString("roleId"),getReqString("roleName"),getReqString("roleDescribe"),
                Integer.parseInt(getReqString("rolePowerSize")),getLoginUserName());
        return OPERSTOR_SUCCESS;
    }

    /** 角色列表
     *
     * @param modelMap
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping("/list")
    public String listRole(ModelMap modelMap){
        Integer curPage = getReqString("p") == null ? 1 : Integer.parseInt(getReqString("p"));
        Integer pageSize = getReqString("pageSize") == null ? 10 : Integer.parseInt(getReqString("pageSize"));

        Page<Role> rolePage = roleService.listRolesPage(curPage,pageSize,getLoginUser().getRole().getRolePowerSize());
        Integer sumPage = rolePage.getTotalPages();
        modelMap.put("sumPage",sumPage);
        modelMap.put("curPage",curPage);
        modelMap.put("rolePage",rolePage);
        return "role/list";
    }

    /** 删除角色
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRole(){
        roleService.removeRole(getReqString("roleId"),getLoginUserName());
        return OPERSTOR_SUCCESS;
    }

    /** 分配权限页面
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/dispatcher",method = RequestMethod.GET)
    public String dispatcherRole(ModelMap modelMap){
        String roldId = getReqString("roleId");
        Role role = roleService.getRole(roldId);

        List<String> roleMenuIds = new ArrayList<>(); //所被分配角色的 menuIds,functionIds
        List<String> roleFunctionIds = new ArrayList<>();
        for(MenuRole menuRole : role.getMenuRoleSet())
            roleMenuIds.add(menuRole.getMenuId());
        for(RoleFunction roleFunction: role.getRoleFunctionSet())
            roleFunctionIds.add(roleFunction.getFunctionId());

        modelMap.put("roleMenuIds",roleMenuIds);
        modelMap.put("roleFunctionIds",roleFunctionIds);
        modelMap.put("undisplayFunctions",functionService.undisplayFunctionsByRoleId(getLoginUser().getRole().getRoleId()));
        modelMap.put("role",role);
        modelMap.put("menuTree",menuService.menuTree(getLoginUser())); //分配着的权限
        return "role/dispatcher";
    }

    /**为角色分配权限操作
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class, DispatcherPowerInterceptor.class})
    @RequestMapping(value = "/dispatcher",method = RequestMethod.POST)
    @ResponseBody
    public String dispatcherRole(){
        roleService.dispatcherRole(getReqStringValues("functionIds"),getReqStringValues("menuIds"),getReqString("roleId"));
        return OPERSTOR_SUCCESS;
    }
}
