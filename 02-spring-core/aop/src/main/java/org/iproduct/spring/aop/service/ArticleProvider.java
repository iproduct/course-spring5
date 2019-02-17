package org.iproduct.spring.aop.service;

import org.iproduct.spring.aop.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
    Article addArticle(Article article);
}
