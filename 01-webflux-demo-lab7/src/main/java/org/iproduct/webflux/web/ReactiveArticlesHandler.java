package org.iproduct.webflux.web;

import org.iproduct.webflux.model.Article;
import org.iproduct.webflux.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class ReactiveArticlesHandler {
    @Autowired
    ArticleService service;

    public Mono<ServerResponse> getAllArticles() {
        return ServerResponse.ok().body(service.findAll(), Article.class);
    }
}
