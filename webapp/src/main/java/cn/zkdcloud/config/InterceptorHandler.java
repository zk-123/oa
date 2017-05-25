package cn.zkdcloud.config;

import cn.zkdcloud.annotation.Before;
import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.entity.Function;
import cn.zkdcloud.entity.Menu;
import cn.zkdcloud.entity.MenuTree;
import cn.zkdcloud.entity.User;
import cn.zkdcloud.service.MenuService;
import cn.zkdcloud.util.Const;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @Author zk
 * @Date 2017/5/21.
 * @Email 2117251154@qq.com
 */
@Component
public class InterceptorHandler implements HandlerInterceptor{

    private static Logger logger = Logger.getLogger(InterceptorHandler.class);

    @Autowired
    MenuService menuService;

    @Autowired
    ApplicationContext applicationContext;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) o;
        Method method = handlerMethod.getMethod();
        Before before = method.getAnnotation(Before.class);
        if(before != null){
            Class<? extends BeforeInterceptor>[] beforeList = before.value();
            for(Class<? extends BeforeInterceptor> oneClazz : beforeList){
                BeforeInterceptor oneEntity= applicationContext.getBean(oneClazz);
                oneEntity.doOperator(httpServletRequest,httpServletResponse); //调用方法
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        if(!(request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))){
            User user = (User) request.getSession().getAttribute(Const.USER_LOGIN);
            if(user == null)
                return;
            MenuTree menuTree  = menuService.menuTree(user);

            if(!menuTree.getMenuTreeList().isEmpty()){ //搜索原url的位置
                for(MenuTree mt : menuTree.getMenuTreeList()){
                    mt.setSpread(isSpread(request.getRequestURI(),mt));
                }
            }

            modelAndView.addObject("menuTree",menuTree);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    //根据需要的url展开对应的选项   （如果再给我一次机会，我一定不会这么设计）
    public boolean isSpread(String url,MenuTree menuTree){
        Set<Function> functionSet = menuTree.getMenu().getFunctionSet();
        for(Function function : functionSet){
            if(function.getFunctionUrl().equals(url)){
                menuTree.setSpread(true);
                return true;
            }
        }
        for(MenuTree mt : menuTree.getMenuTreeList()){
            menuTree.setSpread(isSpread(url,mt));
            if(menuTree.isSpread()) return true;
        }
        return false;
    }
}
