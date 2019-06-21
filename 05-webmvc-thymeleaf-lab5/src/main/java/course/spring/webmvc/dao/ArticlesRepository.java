package course.spring.webmvc.dao;

import course.spring.webmvc.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticlesRepository {
    List<Article> findAll();
    Optional<Article> findById(Long id);
    Article insert(Article article);
    Article save(Article article);
    boolean deleteById(Long articleId);
    long count();
}
