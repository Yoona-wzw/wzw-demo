package com.wzw.eurekaprovider;

import com.wzw.service_provider_api.DateService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateServiceController2
 * @Description
 * @Author wzw
 * @Date 2020/5/9
 * @Version 1.0
 **/
@RestController
public class DateServiceController2 implements DateService {

    @Override
    public String consumer(@RequestParam String param) {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("今天是" + "yyyy年MM月dd日 E kk点mm分");
        String format = simpleDateFormat.format(date);
        return "hello agin"+param+","+format;
    }
}
