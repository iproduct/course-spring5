package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article>  getArticles();
    List<Article> getArticlesByAuthorId(String userId);
    Article createArticle(Article article);
    Article updateArticle(Article article);
    Article getArticleById(String id);
    Article deleteArticle(String id);
}
