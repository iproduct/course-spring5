package course.ws.dao;

import course.ws.model.Article;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Collection<Article> findAll();
    Optional<Article> findById(Long id);
    Article create(Article article);
    Optional<Article> update(Article article);
    Optional<Article> deleteById(Long id);
    long count();
}
