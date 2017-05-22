package cn.zkdcloud.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zk
 * @Date 2017/5/21.
 * @Email 2117251154@qq.com
 */
public interface BeforeInterceptor {
    void doOperator(HttpServletRequest request, HttpServletResponse response);
}
