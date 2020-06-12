package com.wzw.wj.error;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @ClassName ErrorConfig
 * @Description
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
@Component
public class ErrorConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage errorPage = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
        registry.addErrorPages(errorPage);
    }
}
