package com.wzw.wj.config;

import com.wzw.wj.interceptor.LoginInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WebConfiger
 * @Description
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
@SpringBootConfiguration
public class WebConfiger implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }
   /* @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> patternLists=new ArrayList<>();
        patternLists.add("/index.html");
        patternLists.add("/api/login");
        patternLists.add("/api/logout");
        patternLists.add("/api/reg");
        patternLists.add("/api/authentication");
        patternLists.add("/api/regValidate");

        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**")
              .excludePathPatterns(patternLists);
    }*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("POST","GET","PUT","DELETE","OPTIONS")
                .allowedHeaders("content-type", "origin", "accept", "X-Requested-With" , "x-access-token");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/api/file/**").addResourceLocations("file:"+"E:/photos/");
    }

}
