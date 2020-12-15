package org.iproduct.spring.webmvc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan({"org.iproduct.spring.webmvc.service",
        "org.iproduct.spring.webmvc.dao"})
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement
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
    DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        //PostgreSQL database we are using
        dataSource.setDriverClassName(driverClassname);
        dataSource.setJdbcUrl(url);//change url
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

    @Bean
    LocalSessionFactoryBean getSessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(dataSource);
//        factory.setMappingResources("article.hbm.xml");
        factory.setPackagesToScan("org.iproduct.spring.webmvc.model");
        Properties hibernateProperties = new Properties();
        ClassPathResource resource = new ClassPathResource( "hibernate.properties" );
        hibernateProperties.load(resource.getInputStream());
        factory.setHibernateProperties(hibernateProperties);
        return factory;
    }

    @Bean
    HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
