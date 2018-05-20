package org.iproduct.spring.webmvc.service;

import org.iproduct.spring.webmvc.dao.ArticleRepository;
import org.iproduct.spring.webmvc.model.Article;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MockArticleRepository implements ArticleRepository {
    private Map<Long, Article> articles;

    @PostConstruct
    public void init() {
        articles = Arrays.asList(
            new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
            new Article("Dependency Injection", "Should I use DI or lookup ..."),
            new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
        ).stream().collect(Collectors.toConcurrentMap(Article::getId, a -> a));
    }

    @Override
    public Collection<Article> findAll() {
        return articles.values();
    }

    @Override
    public Article find(long id) {
        return articles.get(id);
    }

    @Override
    public Article create(Article article) {
        articles.put(article.getId(), article);
        return article;
    }

    @Override
    public Article update(Article article) {
        articles.put(article.getId(), article);
        return article;
    }

    @Override
    public boolean remove(long id) {
        return articles.remove(id) != null;
    }
}
