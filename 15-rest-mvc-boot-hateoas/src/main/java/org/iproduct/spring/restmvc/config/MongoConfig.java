package org.iproduct.spring.restmvc.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "org.iproduct.spring.restmvc.dao")
public class MongoConfig{
    @Value("${mongo.db.name}")
    private String mongoDbName;

    @Value("${mongo.db.host}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri + "/" + mongoDbName);
    }

}
