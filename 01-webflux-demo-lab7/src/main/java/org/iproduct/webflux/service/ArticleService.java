package org.iproduct.webflux.service;

import org.iproduct.webflux.model.Article;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleService {
    Flux<Article> findAll();
    Mono<Article> findById(String id);
    Mono<Article> create(Article article);
    Mono<Article> update(Article article);
    Mono<Article> delete(String articleId);
    Mono<Long> getCount();
}
