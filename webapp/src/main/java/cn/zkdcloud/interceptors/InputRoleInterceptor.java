package cn.zkdcloud.interceptors;

import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.config.InterceptorHandler;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zk
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
public class InputRoleInterceptor  implements BeforeInterceptor{
    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        String roleName = request.getParameter("roleName");
        String roleDescribe = request.getParameter("roleDescribe");

        if(StrUtil.isBlank(roleName) || StrUtil.notDigitAndLetterAndChinese(roleName))
            throw new TipException("角色名限定(数字,字母，汉字)");
        if(StrUtil.isBlank(roleDescribe))
            throw new TipException("角色描述不能为空");
    }
}
