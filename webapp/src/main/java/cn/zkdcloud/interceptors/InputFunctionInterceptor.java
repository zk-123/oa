package cn.zkdcloud.interceptors;

import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zk
 * @Date 2017/5/21.
 * @Email 2117251154@qq.com
 */
public class InputFunctionInterceptor implements BeforeInterceptor{
    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        String functionName = request.getParameter("functionName");
        String functionUrl = request.getParameter("functionUrl");
        String functionSort = request.getParameter("functionSort");
        String functionDescribe = request.getParameter("functionDescribe");

        if(StrUtil.isBlank(functionName) || StrUtil.notDigitAndLetterAndChinese(functionName))
            throw new TipException("功能名称输入限定（字母，数字，汉字）");
        if(StrUtil.isBlank(functionUrl))
            throw new TipException("url不能为空");
        if(StrUtil.isBlank(functionSort) || !StrUtil.isIntegerDigit(functionSort))
            throw new TipException("功能顺序限定为整数");
        if(StrUtil.isBlank(functionDescribe) || StrUtil.notDigitAndLetterAndChinese(functionDescribe))
            throw new TipException("功能描述输入限定（字母，数字，汉字）");
    }
}
