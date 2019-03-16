package org.iproduct.spring.webmvcdemo.domain;

import org.iproduct.spring.webmvcdemo.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticlesService {
    List<Article> getArticles();
    Article getArticleById(String articleId);
    Article add(Article article);
    Article update(Article article);
    Article delete(String articleId);

}
