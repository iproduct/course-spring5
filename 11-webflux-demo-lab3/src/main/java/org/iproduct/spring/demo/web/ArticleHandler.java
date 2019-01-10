package org.iproduct.spring.demo.web;

import org.iproduct.spring.demo.dao.ArticleRepository;
import org.iproduct.spring.demo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class ArticleHandler {

    @Autowired
    private ArticleRepository articleRepository;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(articleRepository.findAll(), Article.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Article.class)
                .flatMap(article -> articleRepository.insert(article))
                .flatMap(article -> ServerResponse.created(
                        URI.create("/articles/" + article.getId())).build());
    }


}
