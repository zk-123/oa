package cn.zkdcloud.interceptors;

import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.entity.User;
import cn.zkdcloud.exception.ErrorPageException;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.util.Const;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zk
 * @Date 2017/5/23.
 * @Email 2117251154@qq.com
 */
@Component
public class LoginCheckInterceptor implements BeforeInterceptor {
    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        String uid = (String) request.getSession().getAttribute(Const.USER_LOGIN);
        if((request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))) { //ajax请求
            if(uid == null)
                throw new TipException("请先登录");
        } else{
            if(uid == null)
                throw new ErrorPageException("{\"page\":\"login\"}");
        }
    }
}
