package cn.zkdcloud.exception;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * @Author zk
 * @Date 2017/5/17.
 * @Email 2117251154@qq.com
 */
@ControllerAdvice
public class GlobleExceptionHandler {

    private static  Logger logger = Logger.getLogger(GlobleExceptionHandler.class);


    /** 返回提示错误
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(TipException.class)
    @ResponseBody
    String tipExceptionHandler(HttpServletRequest request, HttpServletResponse response,TipException ex){
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(500);
        logger.info("tipException:"+ex.getMessage());
        return ex.getMessage();
    }

    /** 错误页面的跳转
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(ErrorPageException.class)
    String errorPageException(HttpServletRequest request, HttpServletResponse response,ErrorPageException ex){
        JSONObject errorJson = JSONObject.fromObject(ex.getMessage());
        String page = errorJson.getString("page");
        if(page != null) return page;

        String code = errorJson.getString("code");
        String tip = errorJson.getString("tip");
        request.setAttribute("code",code);
        request.setAttribute("tip",tip);
        logger.info("错误页面,"+"code:"+code+",tip:"+tip);

        return "error/error";
    }
}
