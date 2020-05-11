package com.simpleexample.kafka_prod_cons.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;



/**
 * @ClassName ThreadPoolConfig
 * @Description TODO
 * @Author wzw
 * @Date 2020/4/29 17:32
 * @Version 1.0
 **/
@Configuration
public class ThreadPoolConfig {
    @Bean("consumerQueueThreadPool")
    public ExecutorService buildConsumerQueueThreadPool(){
        //ThreadFactoryBuilder是guava提供的并发线程
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("consumer-queue-thread-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MICROSECONDS, new
                ArrayBlockingQueue<Runnable>(5), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }

}
