package com.wen.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wen.core.BaseService;
import com.wen.core.exception.BusinessException;
import com.wen.core.exception.ErrorCodeConst;
import com.wen.core.utils.MapperUtils;
import com.wen.core.utils.StringUtils;
import com.wen.core.web.PageParams;
import com.wen.db.mapper.RoleMapper;
import com.wen.db.model.Role;
import com.wen.web.dto.RoleDto;
import com.wen.web.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author denis.huang
 * @since 2018/8/30 20:03
 */
@Service
public class RoleServiceImpl extends BaseService implements RoleService {
  @Autowired
  private RoleMapper roleMapper;

  @Override
  public Page<RoleDto> selectRoleList(String name, PageParams page) {
    Example ex = new Example(Role.class);
    Example.Criteria criteria = ex.createCriteria();
    if (StringUtils.isNotEmpty(name)) {
      criteria.andEqualTo("name", name);
    }

    PageHelper.startPage(page.getPageNum(), page.getPageSize());
    Page<Role> roles = (Page<Role>) roleMapper.selectByExample(ex);

    return pageConvert(roles, (r) -> MapperUtils.map(r, RoleDto.class));
  }

  @Override
  public int insert(RoleDto role) {
    if (role == null) {
      throw new BusinessException(ErrorCodeConst.ERRCODE_RESOURCE_NOT_EXIST);
    }

    Role po = MapperUtils.map(role, Role.class);
    po.setCreateTime(new Date());
    po.setUpdateTime(new Date());
    roleMapper.insert(po);

    logger.info("添加角色成功,{}", po.getId());

    return po.getId();
  }

  @Override
  public RoleDto selectById(Integer roleId) {
    Role role = roleMapper.selectByPrimaryKey(roleId);
    if (role == null) {
      throwBizException(ErrorCodeConst.ERRCODE_RESOURCE_NOT_EXIST);
    }
    return MapperUtils.map(role, RoleDto.class);
  }

  @Override
  public void updateById(RoleDto roleDto) {
    if (roleDto == null || roleDto.getId() == null) {
      throw new BusinessException(ErrorCodeConst.ERRCODE_PARAM);
    }

    selectById(roleDto.getId());

    Role role = MapperUtils.map(roleDto, Role.class);
    role.setUpdateTime(new Date());

    roleMapper.updateByPrimaryKeySelective(role);

    logger.info("更新角色成功,{}", role.getName());
  }

  @Override
  public void deleteById(Integer roleId) {
    if (roleId == null) {
      throw new BusinessException(ErrorCodeConst.ERRCODE_PARAM);
    }

    selectById(roleId);

    roleMapper.deleteByPrimaryKey(roleId);

    logger.info("删除角色成功,{}", roleId);
  }
}
