package course.spring.intro.dao;

import course.spring.intro.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ArticleRepositoryJpa extends JpaRepository<Article, Long> {
    List<Article> findByTitleLike(String titlePart);
}
