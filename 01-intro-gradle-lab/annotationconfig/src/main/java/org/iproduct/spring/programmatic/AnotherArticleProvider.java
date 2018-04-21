package org.iproduct.spring.programmatic;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@AlternativeProvider
@Component
public class AnotherArticleProvider implements ArticleProvider {

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
            new Article("Another: Welcome to Spring 5", "Spring 5 is great beacuse ..."),
            new Article("Another: Dependency Injection", "Should I use DI or lookup ..."),
            new Article("Another: Spring Beans and Wireing", "There are several ways to configure Spring beans.")
        );
    }
}
