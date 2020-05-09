package com.mqtt.demo.redisDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName RedisString
 * @Description
 * @Author wzw
 * @Date 2020/5/9
 * @Version 1.0
 **/
@RestController
public class RedisString {
    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/sk")
    public void stringSet(){
        //redisUtils.set("k1","hello redis");
//        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("name","wzw");
//        map.put("age",22);
//        redisUtils.hmset("hash",map);
//        redisUtils.lSet("List","one");
//        redisUtils.lSet("List","two");
//        redisUtils.lSet("List","three");
       // redisUtils.sSet("set","set1","set2","set3");
        redisUtils.zSet("zset","zset3",1.3);
        redisUtils.zSet("zset","zset2",1.1);
        redisUtils.zSet("zset","zset1",1.0);
    }
    @PostMapping("/gk")
    public void stringGet(){
       // Map<Object, Object> k1 = redisUtils.hmget("hash");
//        List<Object> list = redisUtils.lGet("List", 1, 3);
//        System.out.println(list);
//        Set<Object> set = redisUtils.sGet("set");
//        System.out.println(set);
        redisUtils.
    }

}
