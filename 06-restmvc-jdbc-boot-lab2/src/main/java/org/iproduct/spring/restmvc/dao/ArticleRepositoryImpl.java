package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.Article;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    
    @Override
    public Collection<Article> findAll() {
        return null;
    }

    @Override
    public Optional<Article> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Article insert(Article article) {
        return null;
    }

    @Override
    public Article save(Article article) {
        return null;
    }

    @Override
    public boolean deleteById(long articleId) {
        return false;
    }
}
