package cn.zkdcloud.interceptors;

import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.entity.User;
import cn.zkdcloud.service.FlowService;
import cn.zkdcloud.service.UserService;
import cn.zkdcloud.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zk
 * @Date 2017/6/4.
 * @Email 2117251154@qq.com
 */
@Component
public class ApproveCheckInterceptor implements BeforeInterceptor {

    @Autowired
    FlowService flowService;

    @Autowired
    UserService userService;

    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUserByUid((String) request.getSession().getAttribute(Const.USER_LOGIN));
        String flowStepId = request.getParameter("flowStepId");
        flowService.isApprove(user.getRole().getRoleId(),flowStepId); //检查是否该由他审批
    }
}
