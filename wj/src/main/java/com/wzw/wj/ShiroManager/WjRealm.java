package com.wzw.wj.ShiroManager;

import com.wzw.wj.pojo.User;
import com.wzw.wj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName WjRealm
 * @Description
 * @Author wzw
 * @Date 2020/5/27
 * @Version 1.0
 **/
@Slf4j
public class WjRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /*获取授权信息*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("-------------权限认证---------");
        String username =(String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }
/*身份认证 根据token中的用户名重数据库获取salt password返回*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username=(String) authenticationToken.getPrincipal();
        String password =new String((char[]) authenticationToken.getCredentials());
        if(authenticationToken.getPrincipal()==null||password==null){
            return null;
        }
        log.info("-------身份认证-----------");

        User user = userService.findUserByUsername(username);
        if(user==null){
            return null;
        }
        String salt = user.getSalt();
        String passwordEncode = user.getPassword();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, passwordEncode, ByteSource.Util.bytes(salt), getName());

        return simpleAuthenticationInfo;
    }
}
