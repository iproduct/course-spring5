package ws.spring.dao;

import ws.spring.model.Article;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Collection<Article> findAll();
    Optional<Article> findById(Long id);
    Article create(Article article);
    int count();
}
