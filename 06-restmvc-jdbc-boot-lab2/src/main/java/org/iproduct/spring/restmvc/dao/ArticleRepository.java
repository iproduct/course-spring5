package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.Article;

import java.util.Collection;

public interface ArticleRepository {
    Collection<Article> findAll();
    Article find(long id);
    Article create(Article article);
    Article update(Article article);
    boolean remove(long articleId);
}
