package com.wen.db.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 用户名
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 头像
   */
  private String header;

  /**
   * 用户类型，0普通用户，1超管
   */
  @Column(name = "user_type")
  private Integer userType;

  /**
   * 删除状态
   */
  private Boolean deleted;

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
   * 获取用户名
   *
   * @return username - 用户名
   */
  public String getUsername() {
    return username;
  }

  /**
   * 设置用户名
   *
   * @param username 用户名
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * 获取密码
   *
   * @return password - 密码
   */
  public String getPassword() {
    return password;
  }

  /**
   * 设置密码
   *
   * @param password 密码
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * 获取邮箱
   *
   * @return email - 邮箱
   */
  public String getEmail() {
    return email;
  }

  /**
   * 设置邮箱
   *
   * @param email 邮箱
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * 获取头像
   *
   * @return header - 头像
   */
  public String getHeader() {
    return header;
  }

  /**
   * 设置头像
   *
   * @param header 头像
   */
  public void setHeader(String header) {
    this.header = header;
  }

  /**
   * 获取用户类型，0普通用户，1超管
   *
   * @return user_type - 用户类型，0普通用户，1超管
   */
  public Integer getUserType() {
    return userType;
  }

  /**
   * 设置用户类型，0普通用户，1超管
   *
   * @param userType 用户类型，0普通用户，1超管
   */
  public void setUserType(Integer userType) {
    this.userType = userType;
  }

  /**
   * 获取删除状态
   *
   * @return deleted - 删除状态
   */
  public Boolean getDeleted() {
    return deleted;
  }

  /**
   * 设置删除状态
   *
   * @param deleted 删除状态
   */
  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
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