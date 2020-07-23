package com.test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.wen.web.dto.UserDto;
import com.wen.web.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unitils.inject.annotation.TestedObject;

/**
 * @author denis.huang
 * @since 2017/6/27
 */
public class SysUserTest extends UnitTestBase {
  @Autowired
  @TestedObject
  private UserService userService;

  @Test
  @DatabaseSetup(TEST_DATA_HOME + "user.xml")
  public void test() {
    UserDto user = userService.findById(1);
    Assert.assertNotNull(user);
  }
}
