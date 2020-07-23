package com.wen.web.service;

import com.github.pagehelper.Page;
import com.wen.core.web.PageParams;
import com.wen.web.dto.UserDto;

/**
 * @author denis.huang
 * @since 2017/10/31
 */
public interface UserService {
  /**
   * 根据username查询
   *
   * @param username
   * @return
   */
  UserDto findByUsername(String username);

  /**
   * 根据id查询
   *
   * @param userId
   * @return
   */
  UserDto findById(Integer userId);

  /**
   * 更新用户信息
   *
   * @param userDto
   */
  void update(UserDto userDto);

  /**
   * 更新用户密码
   *
   * @param userId
   * @param password
   */
  void updatePassword(Integer userId, String password);

  /**
   * 用户分页
   *
   * @param username
   * @param page
   * @return
   */
  Page<UserDto> findUser(String username, PageParams page);
}
