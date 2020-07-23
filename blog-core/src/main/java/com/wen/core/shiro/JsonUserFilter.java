package com.wen.core.shiro;

import com.wen.core.exception.ErrorCodeConst;
import com.wen.core.utils.JsonUtils;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 继承UserFilter过滤器，不通过时返回json而不是跳转页面
 *
 * @author denis.huang
 * @since 2017年2月15日
 */
public class JsonUserFilter extends UserFilter {
  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    JsonUtils.writeToJson(response, ErrorCodeConst.ERRCODE_NOLOGIN);
    return false;
  }
}
