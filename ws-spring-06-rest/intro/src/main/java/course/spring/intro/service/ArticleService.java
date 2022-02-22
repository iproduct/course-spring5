package course.spring.intro.service;

import course.spring.intro.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getAll();
    Article getById(Long id);
    Article create(Article article);
    Article update(Article article);
    Article deleteById(Long id);
    long getCount();
}
