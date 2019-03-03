package org.iproduct.spring.coredemo;

import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:articles.properties")
@ComponentScan(basePackageClasses = org.iproduct.spring.coredemo.ArticleProvider.class)
public class ProgrammaticAnnotationConfig {

    @Bean("provider")
//    @Scope("prototype")
    public ArticleProvider getProvider() {
        return new AlternativeArticleProvider();
    }

    @Bean("presenter")
    public ArticlePresenter getPresenter() {
        ConsoleArticlePresenter presenter = new ConsoleArticlePresenter();
        presenter.setProviders(getProvider());
        return presenter;
    }

    @Bean("presenter2")
    public ArticlePresenter getPresenter2() {
        ConsoleArticlePresenter presenter = new ConsoleArticlePresenter();
        presenter.setProviders(getProvider());
        return presenter;
    }


}
