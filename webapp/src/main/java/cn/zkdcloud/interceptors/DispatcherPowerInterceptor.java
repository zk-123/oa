package cn.zkdcloud.interceptors;

import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.entity.User;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.service.RoleService;
import cn.zkdcloud.service.UserService;
import cn.zkdcloud.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zk
 * @Date 2017/5/23.
 * @Email 2117251154@qq.com
 */
@Component
public class DispatcherPowerInterceptor implements BeforeInterceptor{

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUserByUid((String) request.getSession().getAttribute(Const.USER_LOGIN));
        Role role = roleService.getRole(user.getRole().getRoleId());
        Role disRole = roleService.getRole(request.getParameter("roleId"));
        if(role.getRolePowerSize() >= disRole.getRolePowerSize())
            throw new TipException("权限不够");
    }
}
