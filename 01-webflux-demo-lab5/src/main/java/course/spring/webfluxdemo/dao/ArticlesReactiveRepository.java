package course.spring.webfluxdemo.dao;

import course.spring.webfluxdemo.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ArticlesReactiveRepository extends ReactiveMongoRepository<Article, String> {
    Flux<Article> findAllByAuthor(String author);
}
