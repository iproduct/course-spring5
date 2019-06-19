package course.spring.springcoredemo.dao;

import course.spring.springcoredemo.exception.NonexistingEntityException;
import course.spring.springcoredemo.model.Article;

import java.util.Collection;

public interface ArticlesRepository {
    Collection<Article> findAll();
    Article create(Article article);
    Article update(Article article) throws NonexistingEntityException;
    Article delete(String articleId) throws NonexistingEntityException;
}
