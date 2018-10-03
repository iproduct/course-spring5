package org.iproduct.spring.webflux.webfluxintro.webreactive;

import org.iproduct.spring.webflux.webfluxintro.model.Article;
import org.iproduct.spring.webflux.webfluxintro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ArticleHandler {
    @Autowired
    private ArticleService service;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(service.getArticles(), Article.class);
    }
}
