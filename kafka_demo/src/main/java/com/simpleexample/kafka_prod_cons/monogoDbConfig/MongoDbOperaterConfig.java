package com.simpleexample.kafka_prod_cons.monogoDbConfig;

import com.simpleexample.kafka_prod_cons.utils.MongoDbOperater;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MongoDbOperaterConfig {

    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private String port;
    @Value("${spring.data.mongodb.username}")
    private String username;
    @Value("${spring.data.mongodb.password}")
    private String password;
    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MongoDbOperater getMongoDbOperater(){
        String url = String.format("mongodb://%s:%s@%s:%s/%s", username,
                password, host, port, database);
        return new MongoDbOperater(url);
    }

}
