package org.iproduct.spring.restmvc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataConfig {
    @Autowired
    private Environment env;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassname;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${spring.jpa.properties.hibernate.hbm2ddl.auto}")
    private String hibernateDdl;

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



    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setMappingResources("article.hbm.xml");
//        sessionFactory.setAnnotatedPackages(new String[]{"org.iproduct.spring.restmvc.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", hibernateDdl);
                setProperty("hibernate.dialect", hibernateDialect);
                setProperty("hibernate.globally_quoted_identifiers", "true");
            }
        };
    }
}