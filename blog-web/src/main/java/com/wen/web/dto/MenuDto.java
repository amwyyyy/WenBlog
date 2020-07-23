package com.wen.web.dto;

/**
 * @author denis.huang
 * @since 2017/11/1
 */
public class MenuDto {
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
  private Integer parentId;

  /**
   * 模块排序号
   */
  private Integer orderNum;

  /**
   * 菜单图标样式
   */
  private String iconCls;

  /**
   * 是否隐藏
   */
  private Boolean hidden;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Integer getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
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
}
