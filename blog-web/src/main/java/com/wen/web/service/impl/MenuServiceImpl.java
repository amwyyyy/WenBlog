package com.wen.web.service.impl;

import com.wen.core.BaseService;
import com.wen.core.exception.ErrorCodeConst;
import com.wen.core.mybatis.orderbyhelper.OrderByHelper;
import com.wen.core.utils.AssertUtils;
import com.wen.core.utils.MapperUtils;
import com.wen.db.mapper.MenuMapper;
import com.wen.db.model.Menu;
import com.wen.web.dto.MenuDto;
import com.wen.web.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author denis.huang
 * @since 2017/10/31
 */
@Service
public class MenuServiceImpl extends BaseService implements MenuService {
  @Autowired
  private MenuMapper menuMapper;

  @Override
  public Set<String> findAllMenuName(String username) {
    return menuMapper.findAllMenuName(username);
  }

  @Override
  public List<MenuDto> listMenuByUserId(Integer userId) {
    List<Menu> menuList = menuMapper.listMenuByUserId(userId);
    return MapperUtils.map(menuList, MenuDto.class);
  }

  @Override
  public List<MenuDto> findMenu(Integer level, Integer parentId, String name) {
    Menu menu = new Menu();
    menu.setLevel(level);
    menu.setParentId(parentId);
    menu.setName(name);

    OrderByHelper.orderBy("order_num");
    List<Menu> menuList = menuMapper.select(menu);
    return MapperUtils.map(menuList, MenuDto.class);
  }

  @Override
  public Integer save(MenuDto menu) {
    Menu m = MapperUtils.map(menu, Menu.class);
    if (m.getId() == null) {
      menuMapper.insertSelective(m);
    } else {
      menuMapper.updateByPrimaryKeySelective(m);
    }
    return m.getId();
  }

  @Override
  public List<MenuDto> findMenuByParent(Integer parentId) {
    AssertUtils.notNull(parentId);
    Menu menu = new Menu();
    menu.setParentId(parentId);
    List<Menu> list = menuMapper.select(menu);
    return MapperUtils.map(list, MenuDto.class);
  }

  @Override
  public void deleteById(Integer menuId) {
    AssertUtils.notNull(menuId);
    List<MenuDto> menuList = findMenuByParent(menuId);
    if (!CollectionUtils.isEmpty(menuList)) {
      throwBizException(ErrorCodeConst.ERRCODE_ILLEGAL_OPERATING, "菜单下有子菜单，不能删除");
    }

    menuMapper.deleteByPrimaryKey(menuId);
    logger.info("成功删除菜单，menuId={}", menuId);
  }
}
