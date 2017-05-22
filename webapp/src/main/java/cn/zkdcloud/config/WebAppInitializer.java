package cn.zkdcloud.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 初始化服务器相关设置
 */
@WebListener
@Component
public class WebAppInitializer implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {


  @Resource
  Environment env;

  public WebAppInitializer() {
  }

  /**
   * 保存域名在变量ctx中，freemarker中直接${ctx}
   *
   * @param sce
   */
  public void contextInitialized(ServletContextEvent sce) {
    String domain = env.getProperty("app.domain");
    sce.getServletContext().setAttribute("ctx", domain);
  }

  public void contextDestroyed(ServletContextEvent sce) {
  }

  public void sessionCreated(HttpSessionEvent se) {
  }

  public void sessionDestroyed(HttpSessionEvent se) {
  }

  public void attributeAdded(HttpSessionBindingEvent sbe) {
  }

  public void attributeRemoved(HttpSessionBindingEvent sbe) {
  }

  public void attributeReplaced(HttpSessionBindingEvent sbe) {
  }
}
