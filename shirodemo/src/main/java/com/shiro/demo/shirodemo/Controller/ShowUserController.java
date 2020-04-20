package com.shiro.demo.shirodemo.Controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ShowUserController
 * @Description TODO
 * @Author wzw
 * @Date 2020/4/17 17:05
 * @Version 1.0
 **/
@RestController
public class ShowUserController {
   // @RequiresPermissions("user:show")

}
