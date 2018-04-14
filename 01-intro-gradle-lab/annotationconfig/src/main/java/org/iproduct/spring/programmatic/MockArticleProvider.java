package org.iproduct.spring.programmatic;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("provider")
public class MockArticleProvider implements ArticleProvider{

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
            new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
            new Article("Dependency Injection", "Should I use DI or lookup ..."),
            new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
        );
    }
}
