package course.spring.webfluxdemo.dao;

import course.spring.webfluxdemo.model.Article;

import java.util.List;

public interface ArticlesRepository {
    List<Article> findAll();
    Article create(Article article);
}
