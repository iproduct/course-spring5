package org.iproduct.webflux.web;

import org.iproduct.webflux.model.Article;
import org.iproduct.webflux.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class ReactiveArticlesHandler {
    @Autowired
    ArticleService service;

    public Mono<ServerResponse> getAllArticles(ServerRequest request) {
        return ServerResponse.ok().body(service.findAll(), Article.class);
    }

    public Mono<ServerResponse> addArticle(ServerRequest request) {
        return request.bodyToMono(Article.class)
                .flatMap(service::create)
                .flatMap(created ->
                        ServerResponse.created(
                                request.uriBuilder()
                                        .pathSegment("{id}")
                                        .build(created.getId())).syncBody(created)
                );
    }

    public Mono<ServerResponse> getArticleById(ServerRequest request) {
        return ServerResponse.ok().body(
                service.findById(request.pathVariable("id")), Article.class);
    }
    public Mono<ServerResponse> deleteArticleById(ServerRequest request) {
        return ServerResponse.ok().body(
                service.delete(request.pathVariable("id")), Article.class);
    }
}
