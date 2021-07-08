package course.spring.coredemo.config;

import course.spring.coredemo.dao.ArticleProvider;
import course.spring.coredemo.dao.impl.AlternativeArticleProvider;
import course.spring.coredemo.dao.impl.MockArticleProvider;
import course.spring.coredemo.qualifiers.AlternativeProvider;
import course.spring.coredemo.qualifiers.DefaultProvider;
import course.spring.coredemo.service.ArticlePresenter;
import course.spring.coredemo.service.impl.ConsoleArticlePresenter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("classpath:articles.properties")
public class ProgrammaticAnnotationConfig {
    @Value("${articles.titles}") // SpEL expression
    private String[] articleTitles;

    @Bean(name="alternativeProvider", initMethod = "init")
    @AlternativeProvider
    public AlternativeArticleProvider alternativeProvider() {
        return new AlternativeArticleProvider(articleTitles);
    }

    @Bean("provider")
    @DefaultProvider
    public ArticleProvider provider() {
        return new MockArticleProvider();
    }

    @Bean("presenter")
    @Scope("prototype")
    public ArticlePresenter articlePresenter(@AlternativeProvider ArticleProvider prov) {
        return new ConsoleArticlePresenter(prov);
    }


}
