package com.shiro.demo.shirodemo.shiroConfig;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;

/**
 * @ClassName CustomRealm
 * @Description TODO 自定义的realm
 * @Author wzw
 * @Date 2020/4/17 15:31
 * @Version 1.0
 **/
public class CustomRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("----------权限---------");
        String userName =(String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
       HashSet<String> userSet = new HashSet<>();
        userSet.add("user:show");
        userSet.add("user:admin");
        simpleAuthorizationInfo.setStringPermissions(userSet);
       /*simpleAuthorizationInfo.addRole("admin");
       simpleAuthorizationInfo.addStringPermission("show");*/
        return simpleAuthorizationInfo;
    }
/*获取即将认证的身份信息*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       if(authenticationToken.getPrincipal()==null){
           return null;
       }
        System.out.println("-----------身份认证---------");
        String userName =(String) authenticationToken.getPrincipal();
        System.out.println(userName+"---------------------");
        String password = new String((char[])authenticationToken.getCredentials());
        System.out.println(password+"---------------------");
        //根据用户名从数据库获取密码
        String pwd="1bafaa1a994ff173b904385340d10974";
        if(userName==null){
            throw new AccountException("用户名错误");
        }else if(!password.equals(password)){
            throw new AccountException("密码错误");
        }
        return new SimpleAuthenticationInfo(userName,pwd,ByteSource.Util.bytes(userName+"salt"),getName());
    }
}
