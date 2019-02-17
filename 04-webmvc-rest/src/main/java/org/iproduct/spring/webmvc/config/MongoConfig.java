package org.iproduct.spring.webmvc.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "org.iproduct.spring.webmvc.dao")
public class MongoConfig extends AbstractMongoConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String mongoDB;

    @Value("${spring.data.mongodb.uri}")
    private String mongoURI;

    @Override
    protected String getDatabaseName() {
        return "articles";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("127.0.0.1", 27017);
    }

//    @Override
//    protected String getDatabaseName() {
//        // TODO Auto-generated method stub
//        return mongoDB;
//    }
//
//    @Override
//    public MongoMappingContext mongoMappingContext()
//            throws ClassNotFoundException {
//        // TODO Auto-generated method stub
//        return super.mongoMappingContext();
//    }
//
//    @Override
//    @Bean
//    public Mongo mongo() throws Exception {
//        return new MongoClient(new MongoClientURI(myURI));
//    }
}