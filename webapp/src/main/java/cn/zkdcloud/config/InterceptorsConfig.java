package cn.zkdcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author zk
 * @Date 2017/5/21.
 * @Email 2117251154@qq.com
 */
@Configuration
public class InterceptorsConfig  extends WebMvcConfigurerAdapter{

    @Autowired
    InterceptorHandler interceptorHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorHandler).addPathPatterns("/**");
    }
}
