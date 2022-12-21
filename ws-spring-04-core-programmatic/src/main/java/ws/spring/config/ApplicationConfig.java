package ws.spring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import ws.spring.MainApp;
import ws.spring.client.impl.ConsoleArticlePresenter;
import ws.spring.dao.impl.ArticleRepositoryMemoryImpl;
import ws.spring.model.Article;
import ws.spring.qualifier.RepositoryProvider;
import ws.spring.service.ArticleProvider;
import ws.spring.service.impl.FromPropertyFileArticleProvider;
import ws.spring.service.impl.RepositoryArticleProvider;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(basePackageClasses = MainApp.class)
@PropertySource("classpath:blogs-config.properties")
public class ApplicationConfig {
    @Value("${blogs.titles:Default Article}")
    private String[] titles;

    @Bean(name = "articleRepo")
    @Scope("singleton")
    @Order(1)
    public ArticleRepositoryMemoryImpl articleRepository() {
        return new ArticleRepositoryMemoryImpl();
    }

//    @Bean(name = "repoProvider", initMethod = "init")
//    @RepositoryProvider
//    public RepositoryArticleProvider repoArticleProvider() {
//        RepositoryArticleProvider provider = new RepositoryArticleProvider();
//        provider.setArticleRepository(articleRepository());
//        return provider;
//    }

//    @Bean(name = "fromPropertyFileProvider")
//    @RepositoryProvider
//    @Qualifier("fromPropertyProvider")
//    public FromPropertyFileArticleProvider fromPropertyFileArticleProvider() {
//        return new FromPropertyFileArticleProvider();
//    }

    @Bean(name = "presenter")
    public ConsoleArticlePresenter articleRepository(
            @Qualifier("fromPropertyProvider") ArticleProvider provider) {
        return new ConsoleArticlePresenter(provider);
    }




}
