package com.wen.web.common;

import com.wen.core.utils.StringUtils;
import com.wen.core.web.BaseController;
import com.wen.web.dto.UserDto;
import com.wen.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author denis.huang
 * @since 2017/10/31
 */
public class BizBaseController extends BaseController {
  @Autowired
  protected UserService userService;

  /**
   * 保存登录用户
   */
  protected UserDto saveLoginUser(String username) {
    UserDto user = userService.findByUsername(username);

    if (user != null) {
      Subject subject = SecurityUtils.getSubject();
      subject.getSession(true).setAttribute("user", user);
    }

    return user;
  }

  /**
   * 获取登录的用户
   */
  protected UserDto getLoginUser() {
    Subject subject = SecurityUtils.getSubject();

    UserDto user = (UserDto) subject.getSession().getAttribute("user");
    if (user != null) {
      return user;
    } else if (subject.isRemembered()) {
      // 如果开启记住我，则尝试从cookie中取用户
      String username = (String) subject.getPrincipal();

      if (StringUtils.isNotEmpty(username)) {
        user = saveLoginUser(username);
      }
    }

    if (user != null) {
      return user;
    }
    throw new UnauthenticatedException();
  }

  /**
   * 获取登录用户id
   */
  protected int getLoginUserId() {
    return getLoginUser().getId();
  }
}
