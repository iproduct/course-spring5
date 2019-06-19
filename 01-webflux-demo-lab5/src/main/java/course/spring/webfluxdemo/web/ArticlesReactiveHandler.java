package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ArticlesReactiveService;
import course.spring.webfluxdemo.domain.ArticlesService;
import course.spring.webfluxdemo.exception.IllegalEntityBodyException;
import course.spring.webfluxdemo.exception.NonexistingEntityException;
import course.spring.webfluxdemo.model.Article;
import course.spring.webfluxdemo.model.HttpErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

import static io.netty.handler.codec.rtsp.RtspResponseStatuses.NOT_FOUND;

@Component
public class ArticlesReactiveHandler {
    @Autowired
    private ArticlesReactiveService articlesService;

    public Mono<ServerResponse> getAllArticles(ServerRequest req){
        return ServerResponse.ok().body(articlesService.getAll(), Article.class);
    }

    public Mono<ServerResponse> getArticleById(ServerRequest request) {
        return articlesService.getById(request.pathVariable("id"))
                .flatMap(article -> ServerResponse.ok().syncBody(article))
                .onErrorResume(exception -> ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(
                        new HttpErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage())
                ));
    }

    public Mono<ServerResponse> deleteArticleById(ServerRequest request) {
        return articlesService.delete(request.pathVariable("id"))
                .flatMap(article -> ServerResponse.ok().syncBody(article))
                .onErrorResume(exception -> ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(
                        new HttpErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage())
                ));
    }

    public Mono<ServerResponse> addArticle(ServerRequest request) {
        return request.bodyToMono(Article.class)
            .flatMap(article -> articlesService.add(article))
            .flatMap(created -> ServerResponse.created(
                URI.create(request.path() + "/" + created.getId()))
                .build()
            );
    }

    public Mono<ServerResponse> updateArticle(ServerRequest request) {
        return request.bodyToMono(Article.class)
                .zipWith(articlesService.getById(request.pathVariable("id")))
            .flatMap( tuple -> {
                Article newArt = tuple.getT1();
                Article oldArt = tuple.getT2();
                if(!request.pathVariable("id").equals(newArt.getId())) {
                    return Mono.error(new IllegalEntityBodyException(
                        String.format("IDs in path [%s] and body [%s] are different.",
                                request.pathVariable("id"), newArt.getId())
                    ));
                }
                oldArt.setTitle(newArt.getTitle());
                oldArt.setContent(newArt.getContent());
                oldArt.setModified(LocalDateTime.now());
                return articlesService.update(oldArt);
            }).flatMap(article -> ServerResponse.ok().syncBody(article))
            .onErrorResume(exception -> exception instanceof NonexistingEntityException ?
                ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(
                    new HttpErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage())
                ) :
                ServerResponse.status(HttpStatus.BAD_REQUEST).syncBody(
                    new HttpErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage())
                )
            );
    }
}
