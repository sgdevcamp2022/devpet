package com.smilegate.devpet.appserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = { "com.smilegate.devpet.appserver.repository.mongo" }
)
public class MongoConfig {
    @Value("${server.port}")
    private String serverPort;
    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.host}")
    private String host;


    @Value("${spring.data.mongodb.port}")
    private String port;

    @Value("${spring.data.mongodb.username}")
    private String username;


    @Value("${spring.data.mongodb.password}")
    private String password;


    @Bean("mongoTransactionManager")
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public MongoDatabaseFactory mongoDbFactory() {
        String url = String.format("mongodb://%s:%s@%s:%s/%s?authSource=admin",username,password,host,port,database);
        return new SimpleMongoClientDatabaseFactory(url);
    }

    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}