package org.iproduct.spring.webfluxintro.web;

import org.iproduct.spring.webfluxintro.model.Article;
import org.iproduct.spring.webfluxintro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
public class ArticleHandler {
    @Autowired
    private ArticleService service;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(service.getAllArticles(), Article.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Article.class)
                .flatMap(article -> service.create(article))
                .flatMap(article -> ServerResponse.created(URI.create(
                        "/api/articles/" + article.getId())).build());
    }
}
