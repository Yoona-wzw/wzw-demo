package com.wzw.eurekaconsumer;

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
    @GetMapping("/date")
    public String getDate(@RequestParam String param){
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject("http://localhost:12001/api/test?param={1}", String.class,param);
		//{1}��ռλ��
       // return dateSerivceFeignClient2.consumer(param);
        return res;
    }
}
