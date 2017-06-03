package cn.zkdcloud.interceptors;

import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.util.StrUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zk
 * @Date 2017/5/30.
 * @Email 2117251154@qq.com
 */
@Component
public class InputProcessInterceptor implements BeforeInterceptor {
    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        String processName = request.getParameter("processName");
        String processUrl = request.getParameter("processUrl");
        String[] roleIds = request.getParameterValues("roleIds");

        if(StrUtil.isBlank(processName) || StrUtil.notDigitAndLetterAndChinese(processName))
            throw  new TipException("流程名仅限 数字，字母，汉字");
        if(StrUtil.isBlank(processUrl))
            throw  new TipException("流程模板不能为空");
        if(roleIds == null || roleIds.length == 0)
            throw new TipException("至少选择一个角色来执行");
    }
}
