package org.iproduct.spring.webmvc.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
    @ComponentScan({"org.iproduct.spring.webmvc.service", "org.iproduct.spring.webmvc.dao"})
    @PropertySource("classpath:jdbc.properties")
    public class SpringRootConfig {

        @Value("${jdbc.driverClassName:org.postgresql.Driver}")
        private String driverClassname;

        @Value("${jdbc.url:jdbc:postgresql://localhost/articles}")
        private String url;

        @Value("${jdbc.username:postgres}")
        private String username;

        @Value("${jdbc.password:postgres}")
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
