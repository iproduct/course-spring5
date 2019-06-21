package course.spring.webmvc.dao;

import course.spring.webmvc.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ArticlesRepository extends MongoRepository<Article, String> {
    List<Article> findAll();
    Optional<Article> findById(String id);
}
