package com.wen.web.controller;

import com.wen.core.web.ResultObject;
import com.wen.web.common.BizBaseController;
import com.wen.web.controller.request.MenuSaveReq;
import com.wen.web.controller.response.MenuResp;
import com.wen.web.dto.MenuDto;
import com.wen.web.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author denis.huang
 * @since 2017/11/1
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BizBaseController {
  @Autowired
  private MenuService menuService;

  /**
   * 菜单列表
   *
   * @return
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ResultObject list() {
    int userId = getLoginUserId();
    List<MenuDto> menus = menuService.listMenuByUserId(userId);

    List<MenuResp> respList = menus.stream().map(MenuResp::convertTo).collect(Collectors.toList());

    return buildSuccessResp(respList);
  }

  /**
   * 加载菜单
   *
   * @param level
   * @param parentId
   * @return
   */
  @RequestMapping(value = "/load_menu", method = RequestMethod.GET)
  @RequiresRoles("admin")
  public ResultObject loadMenu(@NotNull Integer level, @NotNull Integer parentId, String name) {
    List<MenuDto> menuList = menuService.findMenu(level, parentId, name);
    List<MenuResp> respList = menuList.stream()
        .map(MenuResp::convertTo)
        .peek(menuResp -> {
          List<MenuDto> menuDtos = menuService.findMenu(menuResp.getLevel() + 1, menuResp.getId(), name);
          menuResp.setHasSubMenu(menuDtos != null && menuDtos.size() > 0);
        })
        .collect(Collectors.toList());
    return buildSuccessResp(respList);
  }

  /**
   * 保存菜单
   *
   * @param req
   * @return
   */
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @RequiresRoles("admin")
  public ResultObject saveMenu(@NotNull MenuSaveReq req) {
    MenuDto menu = req.convert();
    menuService.save(menu);
    return buildSuccessResp();
  }

  /**
   * 删除菜单
   *
   * @param menuId
   * @return
   */
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @RequiresRoles("admin")
  public ResultObject deleteMenu(@NotNull Integer menuId) {
    menuService.deleteById(menuId);
    return buildSuccessResp();
  }
}
