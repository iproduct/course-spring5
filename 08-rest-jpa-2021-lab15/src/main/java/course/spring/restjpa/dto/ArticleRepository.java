package course.spring.restjpa.dto;

import course.spring.restjpa.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTagsInIgnoreCase(Collection<String> filterTags);
    List<Article> findByTitleContainingIgnoreCase(String titlePart);
}
