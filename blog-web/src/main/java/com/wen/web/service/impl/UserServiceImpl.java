package com.wen.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wen.core.BaseService;
import com.wen.core.exception.ErrorCodeConst;
import com.wen.core.utils.AssertUtils;
import com.wen.core.utils.MapperUtils;
import com.wen.core.utils.Md5Utils;
import com.wen.core.utils.StringUtils;
import com.wen.core.web.PageParams;
import com.wen.db.mapper.UserMapper;
import com.wen.db.model.User;
import com.wen.web.dto.UserDto;
import com.wen.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author denis.huang
 * @since 2017/10/31
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
  @Autowired
  private UserMapper userMapper;

  @Override
  public UserDto findByUsername(String username) {
    User user = new User();
    user.setUsername(username);
    return MapperUtils.map(userMapper.selectOne(user), UserDto.class);
  }

  @Override
  public UserDto findById(Integer userId) {
    return MapperUtils.map(userMapper.selectByPrimaryKey(userId), UserDto.class);
  }

  @Override
  public void update(UserDto userDto) {
    AssertUtils.notNull(userDto);
    AssertUtils.notNull(userDto.getId());

    UserDto check = findById(userDto.getId());
    if (check == null) {
      throwBizException(ErrorCodeConst.ERRCODE_RESOURCE_NOT_EXIST);
    }

    User user = MapperUtils.map(userDto, User.class);
    userMapper.updateByPrimaryKeySelective(user);
    logger.info("更新用户信息成功, userId={}", user.getId());
  }

  @Override
  public void updatePassword(Integer userId, String password) {
    AssertUtils.notNull(userId);
    AssertUtils.notEmpty(password);

    User user = new User();
    user.setId(userId);
    user.setPassword(Md5Utils.encode2Base64(password.getBytes()));
    userMapper.updateByPrimaryKeySelective(user);

    logger.info("更新用户密码成功，userId={}", userId);
  }

  @Override
  public Page<UserDto> findUser(String username, PageParams page) {
    Example ex = new Example(User.class);
    Example.Criteria criteria = ex.createCriteria();
    criteria.andEqualTo("deleted", false);

    if (StringUtils.isNotEmpty(username)) {
      criteria.andLike("username", "%" + username + "%");
    }

    PageHelper.startPage(page.getPageNum(), page.getPageSize());
    Page<User> users = (Page<User>) userMapper.selectByExample(ex);
    return pageConvert(users, user -> MapperUtils.map(user, UserDto.class));
  }
}
