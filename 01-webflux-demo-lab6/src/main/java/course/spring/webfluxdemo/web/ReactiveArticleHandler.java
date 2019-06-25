package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ReactiveArticleService;
import course.spring.webfluxdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReactiveArticleHandler {
    @Autowired
    private ReactiveArticleService service;

    public Mono<ServerResponse> getAllArticles(ServerRequest req) {
        return ServerResponse.ok().body(service.getAll(), Article.class);
    }

}
