package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.model.Article;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MockArticleService implements ArticleService {

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
                new Article("Welcome to Spring 5", "Spring 5 is great beacuse ...", "1111111111111111111111"),
                new Article("Dependency Injection", "Should I use DI or lookup ...", "1111111111111111111111"),
                new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.", "1111111111111111111111")
        );
    }

    @Override
    public List<Article> getArticlesByAuthorId(String userId) {
        return null;
    }

    @Override
    public Article createArticle(Article article) {
        return null;
    }

    @Override
    public Article updateArticle(Article article) {
        return null;
    }

    @Override
    public Article getArticleById(String id) {
        return null;
    }

    @Override
    public Article deleteArticle(String id) {
        return null;
    }
}
