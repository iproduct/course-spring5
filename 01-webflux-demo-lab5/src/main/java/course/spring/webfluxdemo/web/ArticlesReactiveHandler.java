package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ArticlesReactiveService;
import course.spring.webfluxdemo.domain.ArticlesService;
import course.spring.webfluxdemo.exception.IllegalEntityBodyException;
import course.spring.webfluxdemo.exception.NonexistingEntityException;
import course.spring.webfluxdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collection;

@Component
public class ArticlesReactiveHandler {
    @Autowired
    private ArticlesReactiveService articlesService;

    public Mono<ServerResponse> getAllArticles(ServerRequest req){
        return ServerResponse.ok().body(articlesService.getAll(), Article.class);
    }

    public Mono<ServerResponse> addArticle(ServerRequest request) {
        return request.bodyToMono(Article.class)
            .flatMap(article -> articlesService.add(article))
            .flatMap(created -> ServerResponse.created(
                    URI.create(request.path() + "/" + created.getId()))
                    .build()
            );
    }

//    public Article addArticle(@RequestBody Article article){
//        return articlesService.add(article);
//    }
//
//    public Article updateArticle(@PathVariable("id") String id,
//                                 @RequestBody Article article)
//            throws NonexistingEntityException, IllegalEntityBodyException {
//        if(!id.equals(article.getId())) {
//            throw new IllegalEntityBodyException(
//                    String.format("ID in body:'%s' different from path:'%s'",
//                            article.getId() ,id));
//        }
//        return articlesService.update(article);
//    }
//
//    public Article deleteArticle(@PathVariable("id") String id) throws NonexistingEntityException {
//        return articlesService.delete(id);
//    }

}
