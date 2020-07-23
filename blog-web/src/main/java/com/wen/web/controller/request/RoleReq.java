package com.wen.web.controller.request;

import com.wen.web.dto.RoleDto;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author denis.huang
 * @since 2018/8/30 19:41
 */
public class RoleReq {
  private Integer id;

  @NotEmpty
  private String name;

  private String remark;

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

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public RoleDto convert() {
    RoleDto role = new RoleDto();
    role.setName(name);
    role.setRemark(remark);
    role.setId(id);
    return role;
  }
}
