package com.wen.web.common;

import com.wen.core.utils.Md5Utils;
import com.wen.web.dto.UserDto;
import com.wen.web.service.MenuService;
import com.wen.web.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 身份认证器
 *
 * @author denis.huang
 * @since 2017年2月15日
 */
@Component("dbRealm")
public class DbRealm extends AuthorizingRealm {
  @Autowired
  private UserService userService;
  @Autowired
  private MenuService menuService;

  @Override
  public String getName() {
    return "dbRealm";
  }

  @Autowired
  public DbRealm(CacheManager cacheManager) {
    this.setCacheManager(cacheManager);
  }

  /**
   * 验证用户名密码
   *
   * @param token
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String username = (String) token.getPrincipal();
    String pwd = new String((char[]) token.getCredentials());

    // 验证用户名密码
    UserDto user = userService.findByUsername(username);
    if (user == null) {
      throw new UnknownAccountException();
    } else if (!user.getPassword().equals(Md5Utils.encode2Base64(pwd.getBytes()))) {
      throw new IncorrectCredentialsException();
    }

    return new SimpleAuthenticationInfo(username, pwd, getName());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String username = (String) principals.getPrimaryPrincipal();

    UserDto user = userService.findByUsername(username);

    Set<String> roles = new HashSet<>();
    roles.add(user.getUserType().equals(0) ? "normal" : "admin");

    // 设置角色和权限
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    authorizationInfo.setRoles(roles);
    authorizationInfo.setStringPermissions(menuService.findAllMenuName(username));
    return authorizationInfo;
  }

  /**
   * 清理认证信息缓存
   */
  public void clearCache() {
    getAuthorizationCache().clear();
  }
}
