package org.iproduct.spring.webmvc.service;

import org.iproduct.spring.webmvc.model.Article;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MockArticleRepository implements ArticleRepository {
    private List<Article> articles = new CopyOnWriteArrayList<Article>();

    @PostConstruct
    public void init() {
        articles.addAll(Arrays.asList(
            new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
            new Article("Dependency Injection", "Should I use DI or lookup ..."),
            new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
        ));
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public void create(Article article) {
        articles.add(article);
    }
}
