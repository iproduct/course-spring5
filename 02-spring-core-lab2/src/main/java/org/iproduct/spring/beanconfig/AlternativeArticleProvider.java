package org.iproduct.spring.beanconfig;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("alternativeProvider")
@AlternativeProvider
public class AlternativeArticleProvider implements ArticleProvider {
    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
                new Article("Alternative: Welcome to Spring 5", "Spring 5 is great beacuse ..."),
                new Article("Alternative: Dependency Injection", "Should I use DI or lookup ..."),
                new Article("Alternative: Spring Beans and Wireing", "There are several ways to configure Spring beans.")
        );
    }
}
