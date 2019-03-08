package patchdemo.service;

import patchdemo.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticlesService {
    List<Article>  getAllArticles();
    Article createArticle(Article article);
    Article updateArticle(Article article);
    Article getArticleById(String id);
    Article deleteArticle(String id);
    void deleteAllArticles();
}
