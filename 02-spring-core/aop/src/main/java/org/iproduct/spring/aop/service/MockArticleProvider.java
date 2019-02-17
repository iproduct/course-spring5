package org.iproduct.spring.aop.service;

import org.iproduct.spring.aop.Article;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository("provider")
public class MockArticleProvider implements ArticleProvider {
    List<Article> articles = new CopyOnWriteArrayList<>(Arrays.asList(
            new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
            new Article("Dependency Injection", "Should I use DI or lookup ..."),
            new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
    ));

    @Override
    public List<Article> getArticles() {
        return articles;

    }

    @Override
    public Article addArticle(Article article) {
        articles.add(article);
        return article;
    }
}
