package com.wzw.eurekaconsumer;

import com.wzw.service_provider_api.DateService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Interface DateSerivceFeignClient2
 * @Description
 * @Author wzw
 * @Date 2020/5/9
 * @Version 1.0
 **/
//@FeignClient(value = "service-provider")
public interface DateSerivceFeignClient2 extends DateService {
}
