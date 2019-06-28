package org.iproduct.spring.restmvc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
public class DataConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassname;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Primary
    DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassname);
        dataSource.setJdbcUrl(url);//change url
        dataSource.setUsername(username);//change username
        dataSource.setPassword(password);//change pwddataSource.setJdbcUrl("jdbc:mysql://localhost:3306/simpsons");

//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(driverClassname);
//        dataSource.setUrl(url);//change url
//        dataSource.setUsername(username);//change username
//        dataSource.setPassword(password);//change pwd

//        //H2 database
//        /*
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");*/
        return dataSource;
    }
}
