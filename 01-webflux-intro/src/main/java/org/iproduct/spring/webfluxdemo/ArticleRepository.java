package org.iproduct.spring.webfluxdemo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
    reactor.core.publisher.Flux<Article> findAllByTitle(org.reactivestreams.Publisher<String> titleStream);
}
