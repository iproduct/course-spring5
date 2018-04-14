package org.iproduct.spring.programmatic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringProgrammaticAnnotationConfig {
    @Bean
    public ArticleProvider provider() {
        return new MockArticleProvider();
    }
    @Bean
    public ArticlePresenter presenter() {
        ArticlePresenter presenter = new ConsoleArticlePresenter();
        presenter.setArticleProvider(provider());
        return presenter;
    }
}
