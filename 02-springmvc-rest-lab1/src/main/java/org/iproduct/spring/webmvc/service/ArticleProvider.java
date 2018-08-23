package org.iproduct.spring.webmvc.service;

import org.iproduct.spring.webmvc.model.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
    Article getArticleById(String id);
    Article addArticle(Article article);
 }
