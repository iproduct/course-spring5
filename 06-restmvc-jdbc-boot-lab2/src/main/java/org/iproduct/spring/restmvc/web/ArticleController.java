package org.iproduct.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.exception.InvalidEntityIdException;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {

    @Autowired
    ArticleService service;

    @GetMapping
    public Collection<Article> getArticles() {
        return service.getArticles();
    }

    @GetMapping("{id}")
    public Article getArticles(@PathVariable long id) {
        return service.getArticleById(id);
    }

    @DeleteMapping("{id}")
    public Article deleteArticles(@PathVariable long id) {
        return service.deleteArticle(id);
    }

    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article created = service.addArticle(article);
        URI location = MvcUriComponentsBuilder.fromMethodName(ArticleController.class, "addArticle", article)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
        return ResponseEntity.created(location).body(created);
//        return ResponseEntity.status(303).location(location).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<Article> addArticle(@PathVariable String id, @RequestBody Article article) {
        if(!article.getId().equals(id)) throw new InvalidEntityIdException(
                String.format("Article ID=%s from path is different from Entity ID=%s", id, article.getId()));
        Article updated = service.updateArticle(article);
        log.info("Article updated: {}", updated);
        return ResponseEntity.ok(updated);
    }
}
