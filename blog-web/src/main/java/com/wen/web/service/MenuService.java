package com.wen.web.service;

import com.wen.web.dto.MenuDto;

import java.util.List;
import java.util.Set;

/**
 * @author denis.huang
 * @since 2017/10/31
 */
public interface MenuService {
  /**
   * 查询用户所有菜单
   *
   * @param username
   * @return
   */
  Set<String> findAllMenuName(String username);

  /**
   * 根据用户id查询菜单
   *
   * @param userId
   * @return
   */
  List<MenuDto> listMenuByUserId(Integer userId);

  /**
   * 查询菜单
   *
   * @param level    层级
   * @param parentId 父id
   * @param name
   * @return
   */
  List<MenuDto> findMenu(Integer level, Integer parentId, String name);

  /**
   * 保存菜单，id为空则新增
   *
   * @param menu
   * @return
   */
  Integer save(MenuDto menu);

  /**
   * 查询子菜单
   *
   * @param parentId
   * @return
   */
  List<MenuDto> findMenuByParent(Integer parentId);

  /**
   * 删除菜单
   *
   * @param menuId
   */
  void deleteById(Integer menuId);
}
