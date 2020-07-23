package com.wen.core.spring.interceptor;

import com.wen.core.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author denis.huang
 * @since 2017/11/3
 */
public class ActionInfoInterceptor extends HandlerInterceptorAdapter {
  private static Logger logger = LoggerFactory.getLogger(ActionInfoInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HandlerMethod hm = (HandlerMethod) handler;

    logger.debug("action info----------" + DateUtils.formatTime(new Date()) + "------------------------");
    logger.debug("Url         : " + request.getMethod() + " " + request.getRequestURI());
    logger.debug("Controller  : " + hm.getBeanType().getName());
    logger.debug("Method      : " + hm.getMethod().getName());
    logger.debug("UrlParam    : " + request.getQueryString());
    logger.debug("Parameter   : " + getParameter(request, hm.getMethodParameters()));
    logger.debug("--------------------------------------------------------------------");
    return super.preHandle(request, response, handler);
  }

  private String getParameter(HttpServletRequest request, MethodParameter[] methodParameter) {
    StringBuilder params = new StringBuilder("{");
    for (MethodParameter parameter : methodParameter) {
      if (params.length() > 1) {
        params.append(", ");
      }
      params.append(parameter.getParameterName()).append(": ");
      params.append(request.getParameter(parameter.getParameterName()));
    }
    params.append("}");
    return params.toString();
  }
}
