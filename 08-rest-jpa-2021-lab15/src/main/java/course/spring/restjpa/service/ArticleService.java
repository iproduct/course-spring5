package course.spring.restjpa.service;


import course.spring.restjpa.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAll();
    Article findById(Long articleId);
    List<Article> findByTitle(String articlename);
    List<Article> findByTags(List<String> filterTags);
    Article create(Article article);
    Article update(Article article);
    Article deleteById(Long articleId);
    long count();
}
