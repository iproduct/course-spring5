package org.iproduct.spring.webmvcsericejpa.web;

import com.sun.jndi.toolkit.url.Uri;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.webmvcsericejpa.dao.ArticleDao;
import org.iproduct.spring.webmvcsericejpa.exceptions.ResourceException;
import org.iproduct.spring.webmvcsericejpa.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.FileSystemException;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleDao articleService;

    @GetMapping
    public Iterable<Article> getArrticles(){
        return articleService.findAll();
    }

    @PostMapping
    public ResponseEntity<Article> postArrticle(
            @RequestBody Article article,
            UriComponentsBuilder uriBuilder){
        article = articleService.save(article);
        URI location = uriBuilder.path("/{id}").buildAndExpand(article.getId()).toUri();
        return ResponseEntity.created(location).body(article);
    }

    @PutMapping("/{id}")
    public Article postArrticle(@PathVariable long id,  @RequestBody Article article){
        if(id != article.getId()){
            throw new ResourceException(HttpStatus.BAD_REQUEST.value(),
                String.format("Resource URL ID=%s different from resource ID=%s.",
                    id, article.getId()));
        }
        return articleService.save(article);
    }

    @ExceptionHandler ({ResourceException.class})
    @Order(1)
    public ResponseEntity<String> handle(ResourceException ex) {
        log.error("Article Controller Error:",ex);
        return ResponseEntity.status(ex.getCode()).body(
            String.format("Error %s: %s", ex.getCode(), ex.getMessage()));
    }
}
