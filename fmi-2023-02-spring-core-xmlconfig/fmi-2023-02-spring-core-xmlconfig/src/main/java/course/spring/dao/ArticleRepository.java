package course.spring.dao;

import course.spring.model.Article;

import java.util.List;
import java.util.Set;

public interface ArticleRepository extends Repository<Article, Long>{
    List<Article> findByKeywords(Set<String> keywords);
}
