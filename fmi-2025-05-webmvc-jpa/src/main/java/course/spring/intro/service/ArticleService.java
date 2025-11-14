package course.spring.intro.service;

import course.spring.intro.entity.Article;

import java.util.List;
import java.util.Set;

public interface ArticleService {
    List<Article> getAllArticles();
    List<Article> getArticlesByTags(Set<String> tags);
    Article getArticleById(int id);
    Article addArticle(Article article);
    Article updateArticle(Article article);
    Article deleteArticleById(int id);
    long getArticleCount();
}
