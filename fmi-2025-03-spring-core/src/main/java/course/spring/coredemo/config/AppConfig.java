package course.spring.coredemo.config;

import course.spring.coredemo.Fmi202503SpringCoreApplication;
import course.spring.coredemo.client.ArticlePresenterClient;
import course.spring.coredemo.service.ArticleProvider;
import course.spring.coredemo.service.impl.ArticleProviderInMemory;
import course.spring.coredemo.util.IdGenerator;
import course.spring.coredemo.util.impl.LongIdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Configuration
@PropertySource("classpath:articles.properties")
@ComponentScan(basePackageClasses =  Fmi202503SpringCoreApplication.class)
public class AppConfig {
    @Bean
    @Scope("prototype")
    public IdGenerator<Long> idGen() {
        return new LongIdGenerator();
    }
    @Bean("articleProviderInMemory")
    @Qualifier("inMemory")
    public ArticleProvider articleProvider() {
        return new ArticleProviderInMemory(idGen());
    }
    @Bean
    public ArticlePresenterClient articlePresenterClient(
            @Qualifier("fromProperties") ArticleProvider articleProvider) {
        var presenter =  new ArticlePresenterClient();
        presenter.setArticleProvider(articleProvider);
        return presenter;
    }
}
