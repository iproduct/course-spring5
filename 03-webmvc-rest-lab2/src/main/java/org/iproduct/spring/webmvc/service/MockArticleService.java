package org.iproduct.spring.webmvc.service;

import org.iproduct.spring.webmvc.model.Article;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MockArticleService implements ArticleService{

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
                new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
                new Article("Dependency Injection", "Should I use DI or lookup ..."),
                new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
        );
    }
}
