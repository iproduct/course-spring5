package course.spring.service;

import course.spring.model.Article;

import java.util.Collection;

public interface ArticleProvider {
    Collection<Article> getArticles();
}
