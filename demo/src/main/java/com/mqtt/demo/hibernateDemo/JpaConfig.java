/*
package com.mqtt.demo.hibernateDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


*/
/**
 * @ClassName JpaConfig
 * @Description TODO
 * @Author wzw
 * @Date 2020/5/7 12:26
 * @Version 1.0
 **//*

@Order(Ordered.HIGHEST_PRECEDENCE) //过滤级别最高
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "com.mqtt.demo.hibernateDemo")
@EntityScan(basePackages = "com.mqtt.demo.hibernateDemo")
@Slf4j
public class JpaConfig {
    @Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
        log.info("jpa加载...");
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
*/
