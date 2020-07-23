package com.wen.web.controller;

import com.wen.core.web.ResultObject;
import com.wen.web.common.BizBaseController;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author denis.huang
 * @since 2017/10/31
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BizBaseController {
  /**
   * 用户登录
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResultObject login(@NotEmpty String username, @NotEmpty String password,
                            @RequestParam(required = false) Boolean rememberMe) {
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    token.setRememberMe(BooleanUtils.isTrue(rememberMe));
    subject.login(token);

    // 保存登录用户
    saveLoginUser(username);

    return buildSuccessResp();
  }

  /**
   * 退出登录
   *
   * @return
   */
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  public ResultObject logout() {
    Subject subject = SecurityUtils.getSubject();
    if (subject != null) {
      subject.logout();
    }
    return buildSuccessResp();
  }

  /**
   * 获取登录用户信息
   *
   * @return
   */
  @RequestMapping(value = "/user", method = RequestMethod.GET)
  @RequiresUser
  public ResultObject userInfo() {
    return buildSuccessResp(getLoginUser());
  }
}
