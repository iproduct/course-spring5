package org.iproduct.spring.mvcdemo.domain;

import org.iproduct.spring.mvcdemo.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles();
    Article add(Article article);
}
