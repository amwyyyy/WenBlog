package com.wen.db.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "menu")
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 菜单名称
   */
  private String name;

  /**
   * 菜单层级
   */
  private Integer level;

  /**
   * 页面文件
   */
  private String page;

  /**
   * 操作链接
   */
  private String url;

  /**
   * 备注
   */
  private String remark;

  /**
   * 父级编号，顶级编号为0
   */
  @Column(name = "parent_id")
  private Integer parentId;

  /**
   * 模块排序号
   */
  @Column(name = "order_num")
  private Integer orderNum;

  /**
   * 菜单图标样式
   */
  @Column(name = "icon_cls")
  private String iconCls;

  /**
   * 是否隐藏
   */
  private Boolean hidden;

  /**
   * 创建时间
   */
  @Column(name = "create_time")
  private Date createTime;

  /**
   * 更新时间
   */
  @Column(name = "update_time")
  private Date updateTime;

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
   * 获取菜单名称
   *
   * @return name - 菜单名称
   */
  public String getName() {
    return name;
  }

  /**
   * 设置菜单名称
   *
   * @param name 菜单名称
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 获取菜单层级
   *
   * @return level - 菜单层级
   */
  public Integer getLevel() {
    return level;
  }

  /**
   * 设置菜单层级
   *
   * @param level 菜单层级
   */
  public void setLevel(Integer level) {
    this.level = level;
  }

  /**
   * 获取页面文件
   *
   * @return page - 页面文件
   */
  public String getPage() {
    return page;
  }

  /**
   * 设置页面文件
   *
   * @param page 页面文件
   */
  public void setPage(String page) {
    this.page = page;
  }

  /**
   * 获取操作链接
   *
   * @return url - 操作链接
   */
  public String getUrl() {
    return url;
  }

  /**
   * 设置操作链接
   *
   * @param url 操作链接
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * 获取备注
   *
   * @return remark - 备注
   */
  public String getRemark() {
    return remark;
  }

  /**
   * 设置备注
   *
   * @param remark 备注
   */
  public void setRemark(String remark) {
    this.remark = remark;
  }

  /**
   * 获取父级编号，顶级编号为0
   *
   * @return parent_id - 父级编号，顶级编号为0
   */
  public Integer getParentId() {
    return parentId;
  }

  /**
   * 设置父级编号，顶级编号为0
   *
   * @param parentId 父级编号，顶级编号为0
   */
  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  /**
   * 获取模块排序号
   *
   * @return order_num - 模块排序号
   */
  public Integer getOrderNum() {
    return orderNum;
  }

  /**
   * 设置模块排序号
   *
   * @param orderNum 模块排序号
   */
  public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
  }

  /**
   * 获取菜单图标样式
   *
   * @return icon_cls - 菜单图标样式
   */
  public String getIconCls() {
    return iconCls;
  }

  /**
   * 设置菜单图标样式
   *
   * @param iconCls 菜单图标样式
   */
  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
  }

  /**
   * 获取是否隐藏
   *
   * @return hidden - 是否隐藏
   */
  public Boolean getHidden() {
    return hidden;
  }

  /**
   * 设置是否隐藏
   *
   * @param hidden 是否隐藏
   */
  public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }

  /**
   * 获取创建时间
   *
   * @return create_time - 创建时间
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * 设置创建时间
   *
   * @param createTime 创建时间
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * 获取更新时间
   *
   * @return update_time - 更新时间
   */
  public Date getUpdateTime() {
    return updateTime;
  }

  /**
   * 设置更新时间
   *
   * @param updateTime 更新时间
   */
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}