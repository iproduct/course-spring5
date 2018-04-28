package org.iproduct.spring.programmatic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Repository;

@Configuration
@PropertySource("classpath:articles.properties")
@ComponentScan(basePackages = "org.iproduct.spring.programmatic",
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Stub.*Repository"),
        excludeFilters = @ComponentScan.Filter(Repository.class))
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
