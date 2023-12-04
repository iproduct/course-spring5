package course.spring.dao;

import course.spring.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepositoryJpa extends JpaRepository<Article, Long> {
    List<Article> findByTitleLike(String titlePart);
}
