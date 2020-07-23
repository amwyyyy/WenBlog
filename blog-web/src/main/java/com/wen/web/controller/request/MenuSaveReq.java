package com.wen.web.controller.request;

import com.wen.web.dto.MenuDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author denis.huang
 * @since 2017/11/13
 */
public class MenuSaveReq {
  private Integer id;

  @NotEmpty
  private String name;

  @NotNull
  private Integer level;

  private String page;

  private String path;

  private String remark;

  @NotNull
  private Integer pid;

  @NotNull
  private Integer orderNum;

  private String iconCls;

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

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getPid() {
    return pid;
  }

  public void setPid(Integer pid) {
    this.pid = pid;
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

  public MenuDto convert() {
    MenuDto menu = new MenuDto();
    menu.setId(id);
    menu.setName(name);
    menu.setLevel(level);
    menu.setOrderNum(orderNum);
    menu.setParentId(pid == null ? 0 : pid);
    menu.setPage(page);
    menu.setUrl(path);
    menu.setRemark(remark);
    menu.setHidden(hidden == null ? false : hidden);
    menu.setIconCls(iconCls);
    return menu;
  }
}
