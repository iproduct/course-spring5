package ws.spring.config;

import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import ws.spring.MainApp;
import ws.spring.client.impl.ConsoleArticlePresenter;
import ws.spring.dao.ArticleRepository;
import ws.spring.dao.impl.ArticleRepositoryMemoryImpl;
import ws.spring.qualifier.MockProvider;
import ws.spring.qualifier.RepositoryProvider;
import ws.spring.service.ArticleProvider;
import ws.spring.service.impl.RepositoryArticleProvider;

@Configuration
@ComponentScan(basePackageClasses = MainApp.class)
public class ApplicationConfig {
    @Bean(name = "articleRepo")
    @Scope("singleton")
    @Order(1)
    public ArticleRepositoryMemoryImpl articleRepository() {
        return new ArticleRepositoryMemoryImpl();
    }

    @Bean(name = "repoProvider", initMethod = "init")
    @RepositoryProvider
    public RepositoryArticleProvider repoArticleProvider() {
        RepositoryArticleProvider provider = new RepositoryArticleProvider();
        provider.setArticleRepository(articleRepository());
        return provider;
    }

    @Bean(name = "presenter")
    public ConsoleArticlePresenter articleRepository(@RepositoryProvider ArticleProvider provider) {
        return new ConsoleArticlePresenter(provider);
    }




}
