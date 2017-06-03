package cn.zkdcloud.processing;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.entity.Flow;
import cn.zkdcloud.entity.FlowStep;
import cn.zkdcloud.entity.Process;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.interceptors.LoginCheckInterceptor;
import cn.zkdcloud.interceptors.PowerCheckInterceptor;
import cn.zkdcloud.service.FlowService;
import cn.zkdcloud.service.ProcessService;
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
 * @Date 2017/6/1.
 * @Email 2117251154@qq.com
 */
@Controller
@RequestMapping("/flow")
public class FlowController extends ContentController{
    private static Logger logger = Logger.getLogger(FlowController.class);

    @Autowired
    private ProcessService processService;

    @Autowired
    private FlowService flowService;
    /** 添加审批审批页面
     *
     * @param modelMap
     * @return
     */
    @Before({LoginCheckInterceptor.class, PowerCheckInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(ModelMap modelMap){
        modelMap.put("processList",processService.getProcessList());
        return "flow/add";
    }

    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(){
        flowService.add(getLoginUser().getUid(),getReqString("processId"),getReqString("flowUrl"));
        return OPERSTOR_SUCCESS;
    }
    /**根据id，获取对应的流程模板地址
     *
     * @return
     */
    @Before(LoginCheckInterceptor.class)
    @RequestMapping(value = "/getUrl",method = RequestMethod.POST)
    @ResponseBody
    public String getProcessUrlById(){
        Process process = processService.getByProcessId(getReqString("processId"));
        return process.getProcessUrl();
    }

    /** 根据用户获取其申请的申请分页
     *
     * @param modelMap
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/myApply",method = RequestMethod.GET)
    public String getFlowPageList(ModelMap modelMap){
        Integer curPage = getReqString("p") == null ? 1 : Integer.parseInt(getReqString("p"));
        Integer pageSize = getReqString("pageSize") == null ? 10 : Integer.parseInt(getReqString("pageSize"));

        Page<Flow> flowPage = flowService.flowPageListByUid(getLoginUser().getUid(),curPage,pageSize);
        Integer sumPage = flowPage.getTotalPages();
        modelMap.put("sumPage",sumPage);
        modelMap.put("curPage",curPage);
        modelMap.put("flowPage",flowPage);
        return "flow/myApply";
    }

    /**待我审批
     *
     * @param modelMap
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/myApprove",method = RequestMethod.GET)
    public String myApprove(ModelMap modelMap){
        Role role = getLoginUser().getRole();
        Integer curPage = getReqString("p") == null ? 1 : Integer.parseInt(getReqString("p"));
        Integer pageSize = getReqString("pageSize") == null ? 10 : Integer.parseInt(getReqString("pageSize"));

        Page<FlowStep> flowStepPage = flowService.flowStepPageByRoleId(curPage,pageSize,role.getRoleId());
        Integer sumPage = flowStepPage.getTotalPages();
        modelMap.put("sumPage",sumPage);
        modelMap.put("curPage",curPage);
        modelMap.put("flowStepPage",flowStepPage);
        return "flow/myApprove";
    }
}
