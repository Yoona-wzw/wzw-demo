package com.shiro.demo.shirodemo.shiroConfig;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @ClassName MD5Utils
 * @Description TODO
 * @Author wzw
 * @Date 2020/4/20 14:05
 * @Version 1.0
 **/
public class MD5Utils {
    public static String MD5pwd(String userName,String pwd){
// salt盐 username + salt
        // 迭代次数
        String md5Pwd = new SimpleHash("MD5", pwd,
                ByteSource.Util.bytes(userName + "salt"), 2).toHex();
        return md5Pwd;
    }

    public static void main(String[] args) {
        String pwd="123456";
        String userName="wzw";
        String s = MD5pwd(userName, pwd);
        System.out.println(s);
    }
}
