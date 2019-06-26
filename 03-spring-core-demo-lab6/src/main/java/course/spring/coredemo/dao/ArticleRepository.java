package course.spring.coredemo.dao;

import course.spring.coredemo.model.Article;

import java.util.Collection;
import java.util.Optional;

public interface ArticleRepository {
    Collection<Article> findAll();
    Optional<Article> findById(String articleId);
    Article insert(Article article);
    Article save(Article article);
    Article deleteById(String articleId);
}
