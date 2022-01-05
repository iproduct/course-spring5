package course.spring.restjpa.dto;

import course.spring.restjpa.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTagsContaining(List<String> filterTags);
    List<Article> findByTitleContaining(String titlePart);
}
