package com.wen.web.service;

import com.github.pagehelper.Page;
import com.wen.core.web.PageParams;
import com.wen.web.dto.RoleDto;

/**
 * @author denis.huang
 * @since 2018/8/30 20:03
 */
public interface RoleService {
  /**
   * 查询角色列表
   *
   * @param name
   * @param page
   * @return
   */
  Page<RoleDto> selectRoleList(String name, PageParams page);

  /**
   * 查询角色
   *
   * @param roleId
   * @return
   */
  RoleDto selectById(Integer roleId);

  /**
   * 新增角色
   *
   * @param role
   * @return
   */
  int insert(RoleDto role);

  /**
   * 更新角色
   *
   * @param role
   */
  void updateById(RoleDto role);

  /**
   * 删除角色
   *
   * @param roleId
   */
  void deleteById(Integer roleId);
}
