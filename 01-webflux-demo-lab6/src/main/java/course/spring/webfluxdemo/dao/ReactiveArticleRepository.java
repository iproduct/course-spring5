package course.spring.webfluxdemo.dao;

import course.spring.webfluxdemo.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveArticleRepository extends
        ReactiveMongoRepository<Article, String> {
}
