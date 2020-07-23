package com.wen.db.mapper;

import com.wen.db.model.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author denis.huang
 */
public interface MenuMapper extends Mapper<Menu> {
  Set<String> findAllMenuName(String username);

  List<Menu> listMenuByUserId(Integer userId);
}