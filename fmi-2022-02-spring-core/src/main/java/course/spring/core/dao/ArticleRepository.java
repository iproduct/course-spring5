package course.spring.core.dao;

import course.spring.core.dao.Repository;
import course.spring.core.model.Article;

import java.util.Collection;
import java.util.List;

public interface ArticleRepository extends Repository<Article, Long> {
    List<Article> findByTags(Collection<String> tags);
}
