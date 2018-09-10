package org.iproduct.spring.restmvc.config;

import com.atomikos.icatch.jta.hibernate4.AtomikosPlatform;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DataSourceConfig {
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

    @Value("${jms.broker.url}")
    private String jmsBrockerUrl;


    @Bean
    public DataSource getDataSource() {
        JDBCXADataSource jdbcxaDataSource = null;
        try {
            jdbcxaDataSource = new JDBCXADataSource();
        } catch (SQLException ex) {
            throw new SQLErrorCodeSQLExceptionTranslator()
                    .translate("Create HSQL JDBCXADataSource", "", ex);
        }
        jdbcxaDataSource.setUrl(url);
//        jdbcxaDataSource.setPinGlobalTxToPhysicalConnection(true);
        jdbcxaDataSource.setUser(username);
        jdbcxaDataSource.setPassword(password);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(jdbcxaDataSource);
        xaDataSource.setUniqueResourceName("xads");
        return xaDataSource;
    }

    @Bean
    public JtaTransactionManager transactionManager(UserTransaction userTransaction,
                                                                            TransactionManager transactionManager) {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(
                userTransaction, transactionManager);
        return jtaTransactionManager;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory();
        activeMQXAConnectionFactory.setBrokerURL(jmsBrockerUrl);
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("xamq");
        atomikosConnectionFactoryBean.setLocalTransactionMode(false);
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
        return atomikosConnectionFactoryBean;
    }


//    @Bean
//    @Primary
//    // Actually not necessary with Spring Boot but needed without it :)
//    DataSource getDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setDriverClassName(driverClassname);
//        dataSource.setJdbcUrl(url);//change url
//        dataSource.setUsername(username);//change username
//        dataSource.setPassword(password);//change pwddataSource.setJdbcUrl("jdbc:mysql://localhost:3306/simpsons");
//
////        BasicDataSource dataSource = new BasicDataSource();
////        dataSource.setDriverClassName(driverClassname);
////        dataSource.setUrl(url);//change url
////        dataSource.setUsername(username);//change username
////        dataSource.setPassword(password);//change pwd
//
////        //H2 database
////        /*
////        dataSource.setDriverClassName("org.h2.Driver");
////        dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
////        dataSource.setUsername("sa");
////        dataSource.setPassword("");*/
//        return dataSource;
//    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(JtaTransactionManager jtaTransactionManager) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setJtaTransactionManager(jtaTransactionManager);
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setMappingResources("article.hbm.xml");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.transaction.jta.platform", AtomikosPlatform.class.getName());
                setProperty("hibernate.hbm2ddl.auto", hibernateDdl);
                setProperty("hibernate.dialect", hibernateDialect);
                setProperty("hibernate.globally_quoted_identifiers", "true");
            }
        };
    }
}