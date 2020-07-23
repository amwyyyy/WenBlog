package com.wen.web.dto;

/**
 * @author denis.huang
 * @since 2018/8/31 15:04
 */
public class RoleDto {
  private Integer id;

  /**
   * 角色名
   */
  private String name;

  /**
   * 备注
   */
  private String remark;

  /**
   * 创建时间
   */
  private String createTime;

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

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}
