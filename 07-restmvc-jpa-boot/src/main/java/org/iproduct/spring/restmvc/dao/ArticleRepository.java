package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.Article;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

public interface ArticleRepository {
    Collection<Article> findAll();
    Optional<Article> findById(long id);
    Article insert(@Valid Article article);
    Article save(Article article);
    boolean deleteById(long articleId);
    long count();
}
