package org.iproduct.spring.restmvc.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataConfig {

    @Value("${spring.datasource.driver}")
    private String driverClassname;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    DataSource getDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        //PostgreSQL database we are using
        dataSource.setDriverClassName(driverClassname);
        dataSource.setUrl(url);//change url
        dataSource.setUsername(username);//change username
        dataSource.setPassword(password);//change pwd

        //H2 database
        /*
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");*/
        return dataSource;
    }
}