package course.spring.intro.service;

import course.spring.intro.entity.Article;
import course.spring.intro.entity.Category;
import course.spring.intro.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface ArticleService {
    Article create(Article article);
    Article update(Article article) throws EntityNotFoundException;
    Article delete(Long articleId) throws EntityNotFoundException;
    List<Article> findAll();
    Article findById(Long articleId) throws EntityNotFoundException;
    List<Article> findByCategory(Category category);
    List<Article> findByTags(Set<String> tags);
    long size();
}
