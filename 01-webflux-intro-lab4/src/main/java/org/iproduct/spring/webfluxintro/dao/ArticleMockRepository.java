package org.iproduct.spring.webfluxintro.dao;

import org.iproduct.spring.webfluxintro.model.Article;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class ArticleMockRepository {
    private List<Article> articles = new CopyOnWriteArrayList<>(
            Arrays.asList(
                    new Article("1111111111111111111111", "New in Spring", "Webflux is here ...", LocalDateTime.now()),
                    new Article("2222222222222222222222", "Dependency Injection", "DI is easy ...", LocalDateTime.now()),
                    new Article("3333333333333333333333", "New in Spring", "Webflux is here ...", LocalDateTime.now()),
                    new Article("4444444444444444444444", "Reactive Java in Spring", "Project Reactor is an infrastructure ...", LocalDateTime.now())
            )
    );

    public Flux<Article> find() {
        return Flux.fromIterable(articles);
    }

    public Mono<Article> save(Article article) {
        article.setId(LocalDateTime.now().toString());
        articles.add(article);
        return Mono.just(article);
    }
}
