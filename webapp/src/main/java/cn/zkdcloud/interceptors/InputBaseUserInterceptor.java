package cn.zkdcloud.interceptors;

import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zk
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
public class InputBaseUserInterceptor implements BeforeInterceptor{
    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(StrUtil.isBlank(username) || StrUtil.notDigitAndLetterAndChinese(username))
            throw new TipException("用户名仅限定于（字母，数字，中文）");
        if(StrUtil.isBlank(password) || StrUtil.notDigitAndLetterAndChinese(password))
            throw new TipException("密码仅限定于（字母，数字，中文）");
    }
}
