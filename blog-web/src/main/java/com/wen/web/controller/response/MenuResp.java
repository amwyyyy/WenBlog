package com.wen.web.controller.response;

import com.wen.web.dto.MenuDto;

/**
 * @author amwyyyy
 * @date 2016/12/11
 */
public class MenuResp {
  /**
   * 菜单id
   */
  private Integer id;
  /**
   * 菜单类型
   */
  private Integer level;
  /**
   * 路径
   */
  private String path;
  /**
   * 页面
   */
  private String page;
  /**
   * 菜单名
   */
  private String name;
  /**
   * 图标
   */
  private String iconCls;
  /**
   * 父节点Id
   */
  private Integer pid;
  /**
   * 排序
   */
  private Integer orderNum;
  /**
   * 是否隐藏
   */
  private Boolean hidden;
  /**
   * 备注
   */
  private String remark;
  /**
   * 是否有子菜单
   */
  private Boolean hasSubMenu;

  public Integer getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getPid() {
    return pid;
  }

  public void setPid(Integer pid) {
    this.pid = pid;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIconCls() {
    return iconCls;
  }

  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
  }

  public Boolean getHidden() {
    return hidden;
  }

  public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }

  public Boolean getHasSubMenu() {
    return hasSubMenu;
  }

  public void setHasSubMenu(Boolean hasSubMenu) {
    this.hasSubMenu = hasSubMenu;
  }

  public static MenuResp convertTo(MenuDto menu) {
    MenuResp resp = new MenuResp();
    resp.setId(menu.getId());
    resp.setPid(menu.getParentId());
    resp.setLevel(menu.getLevel());
    resp.setPage(menu.getPage());
    resp.setIconCls(menu.getIconCls());
    resp.setName(menu.getName());
    resp.setPath(menu.getUrl());
    resp.setOrderNum(menu.getOrderNum());
    resp.setRemark(menu.getRemark());
    resp.setHidden(menu.getHidden());
    return resp;
  }
}
