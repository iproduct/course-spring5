package course.spring.core.service;

import course.spring.core.model.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
}
