package org.iproduct.spring.beanconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("provider")
@MockProvider
public class MockArticleProvider implements ArticleProvider {
    private List<String> titles;

    public MockArticleProvider(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public List<Article> getArticles() {
        return titles.stream().map(title -> new Article(title, title+ " content ..."))
                .collect(Collectors.toList());
//        return Arrays.asList(
//                new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
//                new Article("Dependency Injection", "Should I use DI or lookup ..."),
//                new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
//        );
    }
}
