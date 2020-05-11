//package com.wzw.eurekaconsumer;
//
//import com.netflix.discovery.converters.Auto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * @ClassName DateFeignController
// * @Description
// * @Author wzw
// * @Date 2020/5/9
// * @Version 1.0
// **/
//@RestController
//public class DateFeignController {
//    @Resource
//    DateServiceFeignClient dateServiceFeignClient;
//    @GetMapping("/date")
//    public String getDate(@RequestParam String param){
//        return dateServiceFeignClient.consumer(param);
//    }
//}
