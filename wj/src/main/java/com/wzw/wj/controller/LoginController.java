package com.wzw.wj.controller;

import com.wzw.wj.commons.Result;
import com.wzw.wj.commons.ResultFactory;
import com.wzw.wj.pojo.User;
import com.wzw.wj.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginController
 * @Description
 * @Author wzw
 * @Date 2020/5/22
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user,@RequestParam("rm") Boolean rm, HttpSession session){
        Result result=new Result();
        String username=user.getUsername();
        /*增加shiro认证*/
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, user.getPassword());
        if(rm){
            usernamePasswordToken.setRememberMe(true);//记住我
        }
        try{
            subject.login(usernamePasswordToken);
            result.setCode(200);
            result.setMessage("");
            return result;
        }catch (AuthenticationException ac){
            result.setCode(300);
                result.setMessage("用户名或密码错误");
                return result;
        }


       /* username = HtmlUtils.htmlEscape(username);//html 标签转义， 防止xss攻击
        User userByUsername = userService.findUserByUsername(username);
        if(userByUsername!=null) {
            if (username.equals(userByUsername.getUsername()) && user.getPassword().equals(userByUsername.getPassword())) {
               session.setAttribute("user",user);
                result.setCode(200);
                return result;
            }
        }*/
    }
    @PostMapping(value = "/reg")
    public Result register(@RequestBody User user){
        Result result=new Result();
        String username = user.getUsername();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        boolean exist = userService.isExist(username);
        if(exist){
            String message="用户名已存在";
            result.setCode(300);
            result.setMessage(message);
            return result;
        }
        //生成盐,默认长度16
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        //设置salt次数
        int times=2;
        //得到hash后的密码
        String encodePwd = new SimpleHash("md5", password, salt, times).toString();
        //存储用户信息
        user.setPassword(encodePwd);
        user.setSalt(salt);
        try {
            userService.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        result.setCode(200);
        result.setMessage("注册成功！");
        return result;
    }
    /*登出*/
    @PostMapping("/logout")
    public  Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message="成功登出";
        return ResultFactory.buildSuccessResult(message);
    }
    /*前端每次登陆都要验证*/
    @PostMapping("/authentication")
    public Result authentication(@RequestBody User user){
        if(StringUtils.isNotBlank(user.getUsername())&&StringUtils.isNotBlank(user.getPassword())) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(usernamePasswordToken);
                return ResultFactory.buildSuccessResult("身份认证正确");
            } catch (AuthenticationException ac) {
                return ResultFactory.buildErrorResult("身份验证失败");
            }
        }else{
            return ResultFactory.buildErrorResult("error");
        }
    }
    /*异步表单验证注册的用户名*/
    @PostMapping("/regValidate")
    public String regValidate(@RequestBody User user){
        String username = user.getUsername();
        if(StringUtils.isNotBlank(username)){
            User userByUsername = userService.findUserByUsername(username);
            if(userByUsername!=null){
                return "用户已存在";
            }else{
                return username;
            }
        }
        return null;
    }
}
