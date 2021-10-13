package course.spring.webflux.dao;

import course.spring.webflux.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
    Optional<Article> findByTitle(String title);
}
