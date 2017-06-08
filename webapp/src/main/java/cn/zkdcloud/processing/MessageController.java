package cn.zkdcloud.processing;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.entity.Message;
import cn.zkdcloud.interceptors.LoginCheckInterceptor;
import cn.zkdcloud.interceptors.PowerCheckInterceptor;
import cn.zkdcloud.service.MessageService;
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
 * @Date 2017/6/5.
 * @Email 2117251154@qq.com
 */
@Controller
@RequestMapping("/message")
public class MessageController extends ContentController{

    private static Logger logger = Logger.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    /** 获取用户的消息列表
     *
     * @param modelMap
     * @return
     */
    @Before({LoginCheckInterceptor.class, PowerCheckInterceptor.class})
    @RequestMapping(value = "/my",method = RequestMethod.GET)
    public String getMyMessage(ModelMap modelMap){
        Integer curPage = getReqString("p") == null ? 1 : Integer.valueOf(getReqString("p"));
        Integer pageSize = getReqString("pageSize") == null ? 20 : Integer.valueOf(getReqString("pageSize"));

        Page<Message> messagePage = messageService.getMessagePageList(curPage,pageSize,getLoginUser().getUid());
        Integer sumPage = messagePage.getTotalPages();
        modelMap.put("sumPage",sumPage);
        modelMap.put("curPage",curPage);
        modelMap.put("messagePage",messagePage);
        return "message/list";
    }

    /**标记已读
     *
     * @return
     */
    @Before({LoginCheckInterceptor.class})
    @RequestMapping(value = "/read",method = RequestMethod.GET)
    @ResponseBody
    public String isRead(){
        messageService.isRead(getReqString("mid"));
        return OPERSTOR_SUCCESS;
    }

    /***消息详情
     *
     * @param modelMap
     * @return
     */
    @Before({LoginCheckInterceptor.class})
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String messageDetail(ModelMap modelMap){
        messageService.isRead(getReqString("mid"));
        modelMap.put("message",messageService.getMessageByMid(getReqString("mid")));
        return "message/detail";
    }
}
