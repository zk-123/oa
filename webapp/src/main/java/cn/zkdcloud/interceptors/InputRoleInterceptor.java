package cn.zkdcloud.interceptors;

import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.config.InterceptorHandler;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.entity.User;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.service.RoleService;
import cn.zkdcloud.service.UserService;
import cn.zkdcloud.util.Const;
import cn.zkdcloud.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zk
 * @Date 2017/5/22.
 * @Email 2117251154@qq.com
 */
@Component
public class InputRoleInterceptor  implements BeforeInterceptor{

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUserByUid((String) request.getSession().getAttribute(Const.USER_LOGIN));
        String roleName = request.getParameter("roleName");
        String roleDescribe = request.getParameter("roleDescribe");
        String rolePowerSize = request.getParameter("rolePowerSize");
        Role role = roleService.getRole(user.getRole().getRoleId());

        if(StrUtil.isBlank(roleName) || StrUtil.notDigitAndLetterAndChinese(roleName))
            throw new TipException("角色名限定(数字,字母，汉字)");
        if(StrUtil.isBlank(roleDescribe))
            throw new TipException("角色描述不能为空");
        if(StrUtil.isBlank(rolePowerSize) || !StrUtil.isIntegerDigit(rolePowerSize))
            throw  new TipException("角色权限仅限为整数");
        if(role.getRolePowerSize() >= Integer.parseInt(rolePowerSize))
            throw new TipException("不能设置比自己权限大或等的数");
    }
}
