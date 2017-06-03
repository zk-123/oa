package cn.zkdcloud.processing;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.entity.Process;
import cn.zkdcloud.interceptors.InputProcessInterceptor;
import cn.zkdcloud.interceptors.LoginCheckInterceptor;
import cn.zkdcloud.interceptors.PowerCheckInterceptor;
import cn.zkdcloud.service.ProcessService;
import cn.zkdcloud.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** 审批流程控制类
 * @Author zk
 * @Date 2017/5/28.
 * @Email 2117251154@qq.com
 */
@Controller
@RequestMapping("/process")
public class ProcessController extends ContentController{
    private static Logger logger = Logger.getLogger(ProcessController.class);

    @Autowired
    ProcessService processService;

    @Autowired
    RoleService roleService;

    /**增加流程页面
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class, PowerCheckInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addPage(ModelMap modelMap){
        modelMap.put("roleList",roleService.listRoles());
        return "process/add";
    }

    /**增加流程请求
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class, InputProcessInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(){
        processService.add(getReqString("processName"),getReqString("processDescribe"),
                getReqString("processUrl"),getReqStringValues("roleIds"),getLoginUserName());
        return OPERSTOR_SUCCESS;
    }

    /** 查看流程列表
     *
     * @param modelMap
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String listProcess(ModelMap modelMap){
        Integer curPage = getReqString("p") == null ? 1 : Integer.parseInt(getReqString("p"));
        Integer pageSize = getReqString("pageSize") == null ? 10 : Integer.parseInt(getReqString("pageSize"));

        Page<Process> processPage= processService.getProcessListPage(curPage,pageSize);
        Integer sumPage = processPage.getTotalPages();

        modelMap.put("processPage",processPage);
        modelMap.put("sumPage",sumPage);
        modelMap.put("curPage",curPage);
        return "process/list";
    }

    /**修改流程页面
     *
     * @param modelMap
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/modify",method = RequestMethod.GET)
    public String modify(ModelMap modelMap){
        modelMap.put("modifyProcess",processService.getByProcessId(getReqString("processId")));
        modelMap.put("roleList",roleService.listRoles());
        return "process/modify";
    }

    /**修改流程请求
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class, InputProcessInterceptor.class})
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public String modify(){
        processService.modify(getReqString("processId"),getReqString("processName"),getReqString("processDescribe"),
                getReqString("processUrl"),getReqStringValues("roleIds"),getLoginUserName());
        return OPERSTOR_SUCCESS;
    }

    /**删除指定审批流程
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(){
        processService.delete(getReqString("processId"),getLoginUserName());
        return OPERSTOR_SUCCESS;
    }
}
