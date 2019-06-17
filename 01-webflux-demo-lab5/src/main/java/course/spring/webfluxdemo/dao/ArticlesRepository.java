package course.spring.webfluxdemo.dao;

import course.spring.webfluxdemo.model.Article;

import java.util.Collection;
import java.util.List;

public interface ArticlesRepository {
    Collection<Article> findAll();
    Article create(Article article);
    Article update(Article article);
    Article delete(String articleId);
}
