package course.spring.dao;

import course.spring.model.Article;

import java.util.List;

public interface ArticlesRepository {
    List<Article> findAll();
    Article create(Article article);
}
