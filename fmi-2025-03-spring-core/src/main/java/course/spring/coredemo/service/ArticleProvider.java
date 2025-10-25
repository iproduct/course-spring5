package course.spring.coredemo.service;

import course.spring.coredemo.model.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
    Article createArticle(Article article);
}
