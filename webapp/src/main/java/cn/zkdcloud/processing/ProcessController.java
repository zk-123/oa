package cn.zkdcloud.processing;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.interceptors.LoginCheckInterceptor;
import cn.zkdcloud.interceptors.PowerCheckInterceptor;
import org.apache.log4j.Logger;
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
@Controller("/flow")
public class ProcessController extends ContentController{
    private static Logger logger = Logger.getLogger(ProcessController.class);

    /**增加流程页面
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class, PowerCheckInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addPage(ModelMap modelMap){
        return "flow/add";
    }

    /**增加流程请求
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class,PowerCheckInterceptor.class})
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(){
        return OPERSTOR_SUCCESS;
    }
}
