package course.ws.blogs.service;

import course.ws.blogs.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAllArticles();
    Article findArticleById(Long id);
    Article create(Article article);
    Article update(Article article);
    Article deleteArticleById(Long id);
    long count();
}
