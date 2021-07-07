package course.spring.coredemo.dao;

import course.spring.coredemo.model.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
}
