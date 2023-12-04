package course.spring.service;

import course.spring.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getArticles();
    Article getArticleById(Long articleId);
    Article createArticle(Article article);
    Article updateArticle(Article article);
    Article removeArticle(Long article);
    long getArticleCount();
}
