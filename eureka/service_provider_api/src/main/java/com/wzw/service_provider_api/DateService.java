package com.wzw.service_provider_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName DateService
 * @Description
 * @Author wzw
 * @Date 2020/5/9
 * @Version 1.0
 **/
public interface DateService {
    @GetMapping("/api/test")
    String consumer(@RequestParam("param") String param);
}
