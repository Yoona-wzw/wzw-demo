package com.shiro.demo.shirodemo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author wzw
 * @Date 2020/4/17 15:48
 * @Version 1.0
 **/
@RestController
@Slf4j
public class UserController {

   /* @GetMapping("/login")
    public String dflogin(){
        log.info("======");
        return "这是首页";

    }*/
    @GetMapping("/login")
    public String login(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd){
        //创建一个subject
        Subject subject = SecurityUtils.getSubject();
        //认证前提交的令牌
        UsernamePasswordToken token = new UsernamePasswordToken(userName, pwd);
        //登录验证
        try{
            subject.login(token);
        }catch (UnknownAccountException uae){
                return "未知账户";
        }catch (IncorrectCredentialsException ice){
            return "密码错误";
        }catch (LockedAccountException lae){
            return "账户已锁定";
        }catch (ExcessiveAttemptsException eae){
            return "用户名或密码错误次数过多";
        }catch (AuthenticationException ae){
            return "用户名或密码错误";
        }
        if(subject.isAuthenticated()){
            return "登录成功";

        }else{
            token.clear();
            return "登录失败";
        }


    }
    @RequestMapping("/errorInfo")
    public String errorInfo(){
        return "this is a error";
    }
    @RequiresPermissions("user:show")
    @PostMapping("/user/show")
    public String userShow(){
        return "这是一个用户信息";
    }
}
