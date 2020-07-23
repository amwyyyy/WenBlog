package com.wen.web.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

public class UserPwdReq {
  @NotEmpty
  private String old;

  @NotEmpty
  private String new1;

  @NotEmpty
  private String new2;

  public String getOld() {
    return old;
  }

  public void setOld(String old) {
    this.old = old;
  }

  public String getNew1() {
    return new1;
  }

  public void setNew1(String new1) {
    this.new1 = new1;
  }

  public String getNew2() {
    return new2;
  }

  public void setNew2(String new2) {
    this.new2 = new2;
  }
}
