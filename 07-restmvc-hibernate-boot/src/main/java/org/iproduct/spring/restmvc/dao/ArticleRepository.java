package org.iproduct.spring.restmvc.dao;

import org.hibernate.HibernateException;
import org.iproduct.spring.restmvc.model.Article;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

public interface ArticleRepository {
    Collection<Article> findAll() throws HibernateException;
    Optional<Article> findById(long id) throws HibernateException;
    Article insert(@Valid Article article);
    Article save(Article article);
    Optional<Article> deleteById(long articleId);
    long count();
}
