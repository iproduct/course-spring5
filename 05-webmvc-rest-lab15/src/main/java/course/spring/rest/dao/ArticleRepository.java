package course.spring.rest.dao;

import course.spring.rest.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {
}
