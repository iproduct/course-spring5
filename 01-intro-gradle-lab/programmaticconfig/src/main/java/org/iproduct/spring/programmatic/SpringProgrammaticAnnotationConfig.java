package org.iproduct.spring.programmatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:articles.properties")
public class SpringProgrammaticAnnotationConfig {
//    @Autowired
//    Environment env;

    @Value("${listOfValues}")
    private String[] articleTitles;

    @Bean
    public ArticleProvider provider() {
//        return new MockArticleProvider(env.getProperty("value.from.file", "Default title"));
        return new MockArticleProvider(articleTitles);
    }
    @Bean
    public ArticlePresenter presenter() {
        ArticlePresenter presenter = new ConsoleArticlePresenter();
        presenter.setArticleProvider(provider());
        return presenter;
    }
}
