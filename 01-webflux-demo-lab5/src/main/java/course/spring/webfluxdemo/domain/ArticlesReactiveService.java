package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.exception.NonexistingEntityException;
import course.spring.webfluxdemo.model.Article;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface ArticlesReactiveService {
    Flux<Article> getAll();
    Mono<Article> add(Article article);
    Mono<Article> update(Article article);
    Mono<Article> delete(String articleId);
}
