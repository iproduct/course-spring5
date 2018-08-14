package org.iproduct.spring.beanconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource(value = "classpath:articles.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = "org.iproduct.spring.beanconfig")
//        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Repository"),
//        excludeFilters = @ComponentScan.Filter(Repository.class))
public class SpringProgrammaticAnnotationConfig {
//    @Autowired
//    Environment env;

    @Value("${listOfTitles}")
    private String[] articleTitles;

    @Bean
    public ArticleProvider provider() {
//        return new MockArticleProvider(Arrays.asList(env.getProperty("listOfTitles2", "Default Title")));
        return new MockArticleProvider(Arrays.asList(articleTitles));
    }
    @Bean
    public ArticlePresenter presenter() {
        ArticlePresenter presenter = new ConsoleArticlePresenter();
        presenter.setArticleProvider(provider());
        return presenter;
    }
}
