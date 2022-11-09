package course.spring.core.config;

import course.spring.core.dao.ArticleRepository;
import course.spring.core.dao.impl.ArticleRepoMemoryImpl;
import course.spring.core.dao.impl.LongIdGenerator;
import course.spring.core.service.ArticleProvider;
import course.spring.core.service.Consumer;
import course.spring.core.service.impl.ConsoleArticlePresenter;
import course.spring.core.service.impl.RepositoryArticleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:article.properties")
@ComponentScan("course.spring.core")
@ImportResource("classpath:app-context.xml")
public class ApplicationConfig {
    @Value("${initialGeneratedId}")
    private long initialId;

    //<bean id="idGenerator" class="course.spring.core.dao.impl.LongIdGenerator" scope="prototype"
    //          init-method="init">
    //        <property name="initialId">
    //            <value>${initialGeneratedId}</value>
    //        </property>
    //    </bean>
    @Bean(initMethod = "init")
    public LongIdGenerator idGenerator() {
        var generator = new LongIdGenerator();
        generator.setInitialId(initialId);
        return generator;
    }

    //    <bean id="articleRepository" class="course.spring.core.dao.impl.ArticleRepoMemoryImpl">
    //        <constructor-arg index="0" name="idGen" type="course.spring.core.dao.IdGenerator">
    //            <ref bean="idGenerator"/>
    //        </constructor-arg>
    //    </bean>
    @Bean
    @DependsOn("idGenerator")
    public ArticleRepository articleRepository() {
        return new ArticleRepoMemoryImpl(idGenerator());
    }

    //    <bean id="repoArticleProvider" class="course.spring.core.service.impl.RepositoryArticleProvider"
    //    init-method="init" p:articleRepo-ref="articleRepository" />
    @Bean(initMethod = "init")
    @Primary
    public ArticleProvider repoArticleProvider(ArticleRepository articleRepo) {
        var repoProvider = new RepositoryArticleProvider();
        repoProvider.setArticleRepo(articleRepo);
        return repoProvider;
    }

    //    <bean id="articleConsumer" class="course.spring.core.service.impl.ConsoleArticlePresenter"
    //    factory-bean="articleConsumerFactory" factory-method="createArticleConsumer" />
    @Bean
    public Consumer articleConsumer(ArticleProvider provider) {
        return new ConsoleArticlePresenter(provider);
    }
}
