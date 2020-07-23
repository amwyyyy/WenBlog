package com.wen.web.controller.request;

import com.wen.web.dto.UserDto;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author denis.huang
 * @since 2017/11/2
 */
public class UserUpdateReq {
  /**
   * 用户名
   */
  @NotEmpty
  private String username;

  /**
   * 邮箱
   */
  @NotEmpty
  private String email;

  /**
   * 头像
   */
  private String header;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public UserDto convert() {
    UserDto user = new UserDto();
    user.setUsername(this.username);
    user.setEmail(this.email);
    user.setHeader(this.header);
    return user;
  }
}
