package org.iproduct.spring.webfluxdemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ArticleHandler {

    @Autowired
    ArticleRepository repository;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(this.repository.findAll(), Article.class);
    }
}
