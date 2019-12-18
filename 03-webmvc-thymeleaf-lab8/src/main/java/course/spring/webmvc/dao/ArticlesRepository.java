package course.spring.webmvc.dao;

import course.spring.webmvc.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticlesRepository extends MongoRepository<Article, String> {

}
