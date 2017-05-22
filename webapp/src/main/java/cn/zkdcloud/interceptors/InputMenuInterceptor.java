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
public class InputMenuInterceptor implements BeforeInterceptor{
    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        String menuName = request.getParameter("menuName");
        String menuSort = request.getParameter("menuSort");
        String menuDescribe = request.getParameter("menuDescribe");

        if(StrUtil.isBlank(menuName) || StrUtil.notDigitAndLetterAndChinese(menuName))
            throw new TipException("目录名限定(数字,字母，汉字)");
        if(StrUtil.isBlank(menuDescribe) || StrUtil.notDigitAndLetterAndChinese(menuDescribe))
            throw new TipException("描述限定(数字,字母，汉字)");
        if(StrUtil.isBlank(menuSort) || !StrUtil.isIntegerDigit(menuSort))
            throw new TipException("目录顺序请输入整数");
    }
}
