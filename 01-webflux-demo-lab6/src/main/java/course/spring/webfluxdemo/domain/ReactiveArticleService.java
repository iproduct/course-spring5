package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.exception.NonexisitngEntityException;
import course.spring.webfluxdemo.model.Article;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface ReactiveArticleService {
    Flux<Article> getAll();
    Mono<Article> getById(String articleId);
    Mono<Article> create(Article article);
    Mono<Article> update(Article article);
    Mono<Article> deleteById(String articleId);
}
