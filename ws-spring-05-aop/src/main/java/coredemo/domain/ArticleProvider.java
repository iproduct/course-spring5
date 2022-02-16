package coredemo.domain;

import coredemo.model.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
    Article addArticle(Article article);
}
