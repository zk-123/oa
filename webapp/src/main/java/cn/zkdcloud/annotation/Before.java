package cn.zkdcloud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 验证接口
 * @Author zk
 * @Date 2017/5/21.
 * @Email 2117251154@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
    Class<? extends BeforeInterceptor>[] value();
}
