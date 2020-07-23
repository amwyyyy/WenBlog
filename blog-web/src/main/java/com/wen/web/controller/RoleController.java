package com.wen.web.controller;

import com.github.pagehelper.Page;
import com.wen.core.web.PageParams;
import com.wen.core.web.ResultObject;
import com.wen.web.common.BizBaseController;
import com.wen.web.controller.request.RoleReq;
import com.wen.web.dto.RoleDto;
import com.wen.web.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author denis.huang
 * @since 2018/8/30 19:37
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BizBaseController {

  @Autowired
  private RoleService roleService;

  /**
   * 角色列表
   *
   * @param name
   * @param page
   * @return
   */
  @GetMapping("/list")
  @RequiresRoles("admin")
  public ResultObject list(String name, @Valid PageParams page) {
    Page<RoleDto> list = roleService.selectRoleList(name, page);
    return buildPageResp(list);
  }

  /**
   * 新增角色
   *
   * @param req
   * @return
   */
  @PostMapping("/add")
  @RequiresRoles("admin")
  public ResultObject add(@Valid RoleReq req) {
    RoleDto role = req.convert();
    roleService.insert(role);

    return buildSuccessResp();
  }

  /**
   * 更新角色
   *
   * @param req
   * @return
   */
  @PostMapping("/update")
  @RequiresRoles("admin")
  public ResultObject update(@Valid RoleReq req) {
    RoleDto role = req.convert();
    roleService.updateById(role);

    return buildSuccessResp();
  }

  /**
   * 删除角色
   *
   * @param roleId
   * @return
   */
  @PostMapping("/delete")
  @RequiresRoles("admin")
  public ResultObject del(@NotNull Integer roleId) {
    roleService.deleteById(roleId);
    return buildSuccessResp();
  }
}
