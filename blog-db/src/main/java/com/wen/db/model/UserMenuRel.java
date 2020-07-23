package com.wen.db.model;

import javax.persistence.*;

@Table(name = "user_menu_rel")
public class UserMenuRel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 角色id
   */
  @Column(name = "user_id")
  private Integer userId;

  /**
   * 权限id
   */
  @Column(name = "menu_id")
  private Integer menuId;

  /**
   * @return id
   */
  public Integer getId() {
    return id;
  }

  /**
   * @param id
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * 获取角色id
   *
   * @return user_id - 角色id
   */
  public Integer getUserId() {
    return userId;
  }

  /**
   * 设置角色id
   *
   * @param userId 角色id
   */
  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  /**
   * 获取权限id
   *
   * @return menu_id - 权限id
   */
  public Integer getMenuId() {
    return menuId;
  }

  /**
   * 设置权限id
   *
   * @param menuId 权限id
   */
  public void setMenuId(Integer menuId) {
    this.menuId = menuId;
  }
}