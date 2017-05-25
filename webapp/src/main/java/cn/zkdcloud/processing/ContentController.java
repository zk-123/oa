package cn.zkdcloud.processing;

import cn.zkdcloud.entity.User;
import cn.zkdcloud.util.Const;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author zk
 * @Date 2017/5/17.
 * @Email 2117251154@qq.com
 */
@Controller
public class ContentController {
    protected static String OPERSTOR_SUCCESS = "操作成功";

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    /** 初始化req,resp
     *
     * @param response
     * @param request
     */
    @ModelAttribute
    public void init(HttpServletResponse response,HttpServletRequest request){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /** 获取request中的字符串
     *
     * @param str
     * @return
     */
    public String getReqString(String str){
        return request.getParameter(str);
    }

    /** 获取request中字符串数组
     *
     * @param str
     * @return
     */
    public String[] getReqStringValues(String str){
        return request.getParameterValues(str);
    }

    /** 获取session中的对象
     *
     * @param str
     * @return
     */
    public Object getSessionAttr(String str){
        return session.getAttribute(str);
    }

    /** 获取已登录的用户名
     *
     * @return
     */
    public String getLoginUserName(){
        User user = (User) session.getAttribute(Const.USER_LOGIN);
        return user.getUsername();
    }

    /** 获取登录的user
     *
     * @return
     */
    public User getLoginUser(){
        return  (User) session.getAttribute(Const.USER_LOGIN);
    }

    /** 判断是否登录
     *
     * @return
     */
    public boolean isLogin(){
        return session.getAttribute(Const.USER_LOGIN) == null ? true :false;
    }

}
