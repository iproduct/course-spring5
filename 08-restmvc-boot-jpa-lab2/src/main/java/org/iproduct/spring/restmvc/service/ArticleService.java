package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.model.Article;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface ArticleService {
    Collection<Article> getArticles();
    Article addArticle(@Valid Article article);
    Article updateArticle(Article article);
    Article getArticleById(long id);
    Article deleteArticle(long id);
    List<Article> createArticlesBatch(List<Article> articles);
    long getArticlesCount();
}
