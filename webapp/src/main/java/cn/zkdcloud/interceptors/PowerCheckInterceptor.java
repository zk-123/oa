package cn.zkdcloud.interceptors;

import cn.zkdcloud.annotation.BeforeInterceptor;
import cn.zkdcloud.entity.Role;
import cn.zkdcloud.entity.RolePower;
import cn.zkdcloud.entity.User;
import cn.zkdcloud.exception.ErrorPageException;
import cn.zkdcloud.exception.TipException;
import cn.zkdcloud.service.FunctionService;
import cn.zkdcloud.service.RolePowerService;
import cn.zkdcloud.service.RoleService;
import cn.zkdcloud.service.UserService;
import cn.zkdcloud.util.Const;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
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
public class PowerCheckInterceptor implements BeforeInterceptor{

    private static Logger logger = Logger.getLogger(PowerCheckInterceptor.class);

    @Autowired
    FunctionService functionService;

    @Autowired
    RolePowerService rolePowerService;

    @Override
    public void doOperator(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(Const.USER_LOGIN);
        String functionId = functionService.findFunctionByUrl(request.getRequestURI()).getFunctionId();

        if((request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))){ //ajax请求
            if( user == null)
                throw new TipException("请先登录");

            String roldId = user.getRoleId();
            if(!rolePowerService.findByRoleIdAndFunctionId(roldId,functionId))
                throw new TipException("权限不够");
        }else{
            if(user == null)
                throw new ErrorPageException("{\"page\":\"login\"}");

            String roldId = user.getRoleId();
            if(!rolePowerService.findByRoleIdAndFunctionId(roldId,functionId))
                throw new TipException("权限不够");
        }

    }
}
