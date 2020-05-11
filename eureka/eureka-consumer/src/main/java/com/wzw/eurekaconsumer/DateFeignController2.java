package com.wzw.eurekaconsumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @ClassName DateFeignController2
 * @Description
 * @Author wzw
 * @Date 2020/5/9
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consumer2")
public class DateFeignController2 {
   // @Resource
   // DateSerivceFeignClient2 dateSerivceFeignClient2;
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/date")
    @HystrixCommand(fallbackMethod = "DateFallbackMethod")
    public String getDate(@RequestParam String param){
        //{1}占位符
        String res = restTemplate.getForObject("http://service-provider/api/test?param={1}", String.class, param);
       // return dateSerivceFeignClient2.consumer(param);
        System.out.println("hystrix datefeignController线程名称："+Thread.currentThread().getName()+"获取时间result"+res);
        return res;
    }
    public String DateFallbackMethod(String param){
        return "hystrix 容错 error info-------------";
    }
}
