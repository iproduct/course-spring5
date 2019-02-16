package org.iproduct.spring.webfluxintro.service;

import org.iproduct.spring.webfluxintro.model.Article;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ArticleService {
    Flux<Article> getAllArticles();
    Mono<Article> create(Article article);
}
