package org.iproduct.spring.webmvc.dao;

import org.iproduct.spring.webmvc.model.Article;

import java.util.Collection;
import java.util.List;

public interface ArticleDao {
    Collection<Article> findAll();
    Article find(long id);
    Article create(Article article);
    Article update(Article article);
    Article remove(long articleId);
}
