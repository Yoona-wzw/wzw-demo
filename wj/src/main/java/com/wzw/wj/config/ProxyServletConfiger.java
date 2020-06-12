package com.wzw.wj.config;

import com.google.common.collect.ImmutableMap;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.Map;

/**
 * @ClassName ProxyServletConfiger
 * @Description
 * @Author wzw
 * @Date 2020/6/1
 * @Version 1.0
 **/
@Configuration
public class ProxyServletConfiger {
    /*@Value("${proxy.servlet_url}")
    private String servletUrl;
    @Value("${proxy.target_url}")
    private String targetUrl;

    @Bean
    public Servlet createProxyServlet(){
        return new ProxyServlet();
    }
    @Bean
    public ServletRegistrationBean proxyServletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(createProxyServlet(), servletUrl);
        //设置网址以及参数
        Map<String, String> params = ImmutableMap.of(
                "targetUri", targetUrl,
                "log", "true");
        registrationBean.setInitParameters(params);
        return registrationBean;
    }*/
}
