package course.ws.service;

import course.ws.model.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
}
