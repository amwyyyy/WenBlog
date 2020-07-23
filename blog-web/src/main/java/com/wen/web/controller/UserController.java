package com.wen.web.controller;

import com.github.pagehelper.Page;
import com.wen.core.exception.BusinessException;
import com.wen.core.exception.ErrorCodeConst;
import com.wen.core.utils.Md5Utils;
import com.wen.core.web.PageParams;
import com.wen.core.web.ResultObject;
import com.wen.web.common.BizBaseController;
import com.wen.web.controller.request.UserPwdReq;
import com.wen.web.controller.request.UserUpdateReq;
import com.wen.web.dto.UserDto;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 用户管理
 *
 * @author denis.huang
 * @since 2017/11/2
 */
@Controller
@RequestMapping("/user")
public class UserController extends BizBaseController {
  /**
   * 更新用户信息
   *
   * @param req
   * @return
   */
  @RequiresUser
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ResultObject update(@Valid UserUpdateReq req) {
    // 更新用户信息
    UserDto userDto = req.convert();
    userDto.setId(getLoginUserId());
    userService.update(userDto);

    // 更新用户登录信息
    userDto = userService.findById(userDto.getId());
    saveLoginUser(userDto.getUsername());

    return buildSuccessResp();
  }

  /**
   * 更新密码
   *
   * @param req
   * @return
   */
  @RequiresUser
  @RequestMapping(value = "/update_pwd", method = RequestMethod.POST)
  public ResultObject updatePwd(@Valid UserPwdReq req) {
    UserDto user = userService.findById(getLoginUserId());
    if (!user.getPassword().equals(Md5Utils.encode2Base64(req.getOld().getBytes()))) {
      throw new BusinessException(ErrorCodeConst.ERRCODE_PARAM, "旧密码不正确");
    }

    if (!req.getNew1().equals(req.getNew2())) {
      throw new BusinessException(ErrorCodeConst.ERRCODE_PARAM, "两次输入新密码不同");
    }

    userService.updatePassword(user.getId(), req.getNew1());

    return buildSuccessResp();
  }

  /**
   * 用户列表
   *
   * @param page
   * @param username
   * @return
   */
  @RequiresUser
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ResultObject list(@Valid PageParams page, String username) {
    Page<UserDto> list = userService.findUser(username, page);
    return buildPageResp(list);
  }
}
