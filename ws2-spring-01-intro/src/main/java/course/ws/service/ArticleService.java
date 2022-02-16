package course.ws.service;

import course.ws.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAllArticles();
    Article findArticleById(Long id);
    Article create(Article article);
    Article update(Article article);
    Article deleteArticleById(Long id);
    long count();
}
