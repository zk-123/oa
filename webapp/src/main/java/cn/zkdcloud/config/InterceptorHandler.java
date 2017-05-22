package cn.zkdcloud.config;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.annotation.BeforeInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Author zk
 * @Date 2017/5/21.
 * @Email 2117251154@qq.com
 */
@Component
public class InterceptorHandler implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) o;
        Method method = handlerMethod.getMethod();
        Before before = method.getAnnotation(Before.class);
        if(before != null){
            Class<? extends BeforeInterceptor>[] beforeList = before.value();
            for(Class<? extends BeforeInterceptor> oneClazz : beforeList){
                BeforeInterceptor oneEntity= oneClazz.newInstance();
                oneEntity.doOperator(httpServletRequest,httpServletResponse);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
