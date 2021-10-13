package course.spring.webflux.web;

import course.spring.webflux.dao.ArticleRepository;
import course.spring.webflux.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ArticleHandler {
    @Autowired
    private ArticleRepository articleRepo;

    public Mono<ServerResponse> getAll(ServerRequest req) {
        return ServerResponse.ok().body(articleRepo.findAll(), Article.class);
    }
}
