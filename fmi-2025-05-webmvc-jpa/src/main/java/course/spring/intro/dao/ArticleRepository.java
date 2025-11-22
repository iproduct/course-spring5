package course.spring.intro.dao;

import course.spring.intro.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    public List<Article> findByTagsContainingIgnoreCase(Set<String> tags);
}
