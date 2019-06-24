package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.exception.NonexisitngEntityException;
import course.spring.webfluxdemo.model.Article;

import java.util.Collection;

public interface ArticleService {
    Collection<Article> getAll();
    Article getById(String articleId) throws NonexisitngEntityException;
    Article create(Article article);
    Article update(Article article);
    Article deleteById(String articleId) throws NonexisitngEntityException;
}
