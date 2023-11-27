package course.spring.dao;

import course.spring.model.Article;
import course.spring.model.ArticleCreateDTO;

import java.util.List;
import java.util.Set;

public interface ArticleRepository extends MyRepository<Article, Long> {
    Article create(ArticleCreateDTO createDTO);
    List<Article> findByKeywords(Set<String> keywords);
}
