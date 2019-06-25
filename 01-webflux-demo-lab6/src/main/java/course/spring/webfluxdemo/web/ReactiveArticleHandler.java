package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ReactiveArticleService;
import course.spring.webfluxdemo.exception.InvalidEntityDataException;
import course.spring.webfluxdemo.model.Article;
import course.spring.webfluxdemo.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class ReactiveArticleHandler {
    @Autowired
    private ReactiveArticleService service;

    public Mono<ServerResponse> getAllArticles(ServerRequest req) {
        return ServerResponse.ok().body(service.getAll(), Article.class);
    }

    public Mono<ServerResponse> getArticleById(ServerRequest req) {
        return service.getById(req.pathVariable("id"))
            .flatMap(art -> ServerResponse.ok().syncBody(art));
    }

    public Mono<ServerResponse> addArticle(ServerRequest req) {
        return req.bodyToMono(Article.class)
            .flatMap(service::create)
            .flatMap(art -> ServerResponse.created(
                    req.uriBuilder().pathSegment("{id}").build(art.getId()))
                .syncBody(art))
            .onErrorResume(ex ->
                ServerResponse.badRequest().syncBody(
                    new ErrorResponse(BAD_REQUEST, ex.getMessage())
                )
            );

    }

    public Mono<ServerResponse> updateArticle(ServerRequest request) {
        return request.bodyToMono(Article.class)
            .flatMap( article -> request.pathVariable("id").equals(article.getId()) ?
                service.update(article) :
                Mono.error(new InvalidEntityDataException(
                    String.format("IDs in path [%s] and body [%s] are different.",
                        request.pathVariable("id"), article.getId())
                ))
            ).flatMap(article -> ServerResponse.ok().syncBody(article));
    }

}
