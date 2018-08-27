package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.model.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
    Article getArticleById(String id);
    Article addArticle(Article article);
 }
