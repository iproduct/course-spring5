package org.iproduct.spring.webflux.webflux;

import org.iproduct.spring.webflux.dao.ArticleRepository;
import org.iproduct.spring.webflux.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ArticleHandler {
    @Autowired
    ArticleRepository repo;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(repo.findAll(), Article.class);
    }

}
