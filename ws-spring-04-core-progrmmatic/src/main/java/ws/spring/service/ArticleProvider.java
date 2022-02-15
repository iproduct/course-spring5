package ws.spring.service;

import ws.spring.model.Article;

import java.util.Collection;
import java.util.List;

public interface ArticleProvider {
    Collection<Article> getArticles();
}
