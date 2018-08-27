package org.iproduct.spring.demo.beanconfig.service;

import org.iproduct.spring.demo.beanconfig.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
    Article addArticle(Article article);
}
