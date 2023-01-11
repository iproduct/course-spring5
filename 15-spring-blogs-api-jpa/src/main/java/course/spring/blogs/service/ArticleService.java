package course.spring.blogs.service;

import course.spring.blogs.exception.NonexistingEntityException;
import course.spring.blogs.entity.Article;
import course.spring.blogs.exception.InvalidEntityDataException;

import java.util.List;
/**
 * ArticleService interface
 */
public interface ArticleService {
    /**
     * @return list all {@link Article} entities
     */
    List<Article> getAllArticles();
    /**
     * @param id the ID of Article to be found
     * @return The Article with given ID, if such exists
     * @throws NonexistingEntityException when the Article with given ID does not exist
     */
    Article getArticleById(Long id) throws NonexistingEntityException;
    Article create(Article article);
    List<Article> createBatch(List<Article> articles);
    Article update(Article article) throws NonexistingEntityException, InvalidEntityDataException;
    Article deleteArticleById(Long id) throws NonexistingEntityException;
    long getArticlesCount();
}
