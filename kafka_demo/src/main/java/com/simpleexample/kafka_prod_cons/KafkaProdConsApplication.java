package com.simpleexample.kafka_prod_cons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaProdConsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProdConsApplication.class, args);
    }

}
