package org.iproduct.spring.demo.beanconfig;

import org.iproduct.spring.demo.beanconfig.impl.ConsoleArticlePresenter;
import org.iproduct.spring.demo.beanconfig.impl.MockArticleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Configuration
@PropertySource(value = "classpath:articles.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = "org.iproduct.spring.demo.beanconfig")
//        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Stub.*Repository"),
//        excludeFilters = @ComponentScan.Filter(Repository.class))
public class SpringProgrammaticAnnotationConfig {
//    @Autowired
//    Environment env;

    @Value("${listOfTitles}")
    private String[] articleTitles;

    @Bean
    public ArticleProvider provider() {
//        return new MockArticleProvider(env.getProperty("value.from.file", "Default title"));
        ArticleProvider provider = new MockArticleProvider(articleTitles);
        return provider;
    }
    @Bean
    public ArticlePresenter presenter() {
        ArticlePresenter presenter = new ConsoleArticlePresenter();
        presenter.setArticleProvider(provider());
        return presenter;
    }
}
