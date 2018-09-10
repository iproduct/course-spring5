package org.iproduct.spring.webflux.web;

import org.iproduct.spring.webflux.dao.ArticleRepository;
import org.iproduct.spring.webflux.model.Article;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class ArticleHandler {
    private final ArticleRepository repo;

    public ArticleHandler(ArticleRepository repo) {
        this.repo = repo;
    }

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(repo.findAll(), Article.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Article.class)
                .flatMap(article -> repo.save(article))
                .flatMap(a -> ServerResponse.created(URI.create("/api/aticles/" + a.getId()))
                        .body(Mono.just(a), Article.class));
    }
}
