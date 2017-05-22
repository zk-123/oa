package cn.zkdcloud.processing;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.entity.Function;
import cn.zkdcloud.interceptors.InputFunctionInterceptor;
import cn.zkdcloud.service.FunctionService;
import cn.zkdcloud.service.MenuService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/function")
public class FunctionController extends ContentController{
    private  static Logger logger = Logger.getLogger(FunctionController.class);

    @Autowired
    FunctionService functionService;

    @Autowired
    MenuService menuService;

    /** 添加功能页面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addFunction(ModelMap modelMap){
        modelMap.put("menuList",menuService.getMenuList());
        return "function/add";
    }

    /** 添加功能操作
     *
     * @return
     */
    @Before(InputFunctionInterceptor.class)
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addFunction(){
        functionService.addFunction(getReqString("menuId"),getReqString("functionName"),getReqString("functionUrl"),
                getReqString("functionDescribe"),"haha", Integer.valueOf(getReqString("functionSort")));
        return OPERSTOR_SUCCESS;
    }

    /** 功能列表页
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String listFunction(ModelMap modelMap){
        Integer curPage = getReqString("p") == null ? 1 : Integer.valueOf(getReqString("p"));
        Integer pageSize = getReqString("pageSize") == null ? 10 : Integer.valueOf(getReqString("pageSize"));
        Page<Function> functionPage = functionService.functionPage(curPage,pageSize);
        Integer sumPage = functionPage.getTotalPages();

        modelMap.put("functionPage",functionPage);
        modelMap.put("sumPage",sumPage);
        modelMap.put("curPage",curPage);
        return "function/list";
    }

    /**修改功能页
     *
     * @return
     */
    @RequestMapping(value = "/modify",method = RequestMethod.GET)
    public String modifyFunction(ModelMap modelMap){
        String funcitonId = getReqString("functionId");
        Function modifyFunction = functionService.findFunctionById(funcitonId);

        modelMap.put("modifyFunction",modifyFunction);
        modelMap.put("menuList",menuService.getMenuList());
        return "function/modify";
    }

    /**修改功能操作
     *
     * @return
     */
    @Before(InputFunctionInterceptor.class)
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public String modifyFunction(){
        functionService.modifyFunction(getReqString("functionId"),getReqString("functionName"),getReqString("functionDescribe"),
                Integer.parseInt(getReqString("functionSort")),getReqString("functionUrl"),getReqString("menuId"),"haha");
        return OPERSTOR_SUCCESS;
    }

    /**删除指定功能
     *
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String removeFunction(){
        functionService.removeFunction(getReqString("functionId"),"haha");
        return OPERSTOR_SUCCESS;
    }
}
