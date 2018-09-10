package org.iproduct.spring.restmvc.config;

import com.atomikos.icatch.config.UserTransactionService;
import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.icatch.jta.hibernate4.AtomikosPlatform;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.autoconfigure.transaction.jta.JtaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.XADataSourceWrapper;
import org.springframework.boot.jms.XAConnectionFactoryWrapper;
import org.springframework.boot.jta.atomikos.AtomikosDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.jta.atomikos.AtomikosProperties;
import org.springframework.boot.jta.atomikos.AtomikosXAConnectionFactoryWrapper;
import org.springframework.boot.jta.atomikos.AtomikosXADataSourceWrapper;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.StringUtils;

import javax.jms.Message;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.io.File;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties({ AtomikosProperties.class, JtaProperties.class })
@ConditionalOnClass({ JtaTransactionManager.class, UserTransactionManager.class })
@ConditionalOnMissingBean(PlatformTransactionManager.class)
public class AtomikosJtaConfiguration {

    private final JtaProperties jtaProperties;

    private final TransactionManagerCustomizers transactionManagerCustomizers;


    AtomikosJtaConfiguration(JtaProperties jtaProperties,
                             ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        this.jtaProperties = jtaProperties;
        this.transactionManagerCustomizers = transactionManagerCustomizers
                .getIfAvailable();
    }

    @Bean(initMethod = "init", destroyMethod = "shutdownWait")
    @ConditionalOnMissingBean(UserTransactionService.class)
    public UserTransactionServiceImp userTransactionService(
            AtomikosProperties atomikosProperties) {
        Properties properties = new Properties();
        if (StringUtils.hasText(this.jtaProperties.getTransactionManagerId())) {
            properties.setProperty("com.atomikos.icatch.tm_unique_name",
                    this.jtaProperties.getTransactionManagerId());
        }
        properties.setProperty("com.atomikos.icatch.log_base_dir", getLogBaseDir());
        properties.putAll(atomikosProperties.asProperties());
        return new UserTransactionServiceImp(properties);
    }

    private String getLogBaseDir() {
        if (StringUtils.hasLength(this.jtaProperties.getLogDir())) {
            return this.jtaProperties.getLogDir();
        }
        File home = new ApplicationHome().getDir();
        return new File(home, "transaction-logs").getAbsolutePath();
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConditionalOnMissingBean
    public UserTransactionManager atomikosTransactionManager(
            UserTransactionService userTransactionService) throws Exception {
        UserTransactionManager manager = new UserTransactionManager();
        manager.setStartupTransactionService(false);
        manager.setForceShutdown(true);
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean(XADataSourceWrapper.class)
    public AtomikosXADataSourceWrapper xaDataSourceWrapper() {
        return new AtomikosXADataSourceWrapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public static AtomikosDependsOnBeanFactoryPostProcessor atomikosDependsOnBeanFactoryPostProcessor() {
        return new AtomikosDependsOnBeanFactoryPostProcessor();
    }

    @Bean
    public JtaTransactionManager transactionManager(UserTransaction userTransaction,
                                                    TransactionManager transactionManager) {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(
                userTransaction, transactionManager);
        if (this.transactionManagerCustomizers != null) {
            this.transactionManagerCustomizers.customize(jtaTransactionManager);
        }
        return jtaTransactionManager;
    }

    @Configuration
    @ConditionalOnClass(Message.class)
    static class AtomikosJtaJmsConfiguration {

        @Bean
        @ConditionalOnMissingBean(XAConnectionFactoryWrapper.class)
        public AtomikosXAConnectionFactoryWrapper xaConnectionFactoryWrapper() {
            return new AtomikosXAConnectionFactoryWrapper();
        }
    }


}
