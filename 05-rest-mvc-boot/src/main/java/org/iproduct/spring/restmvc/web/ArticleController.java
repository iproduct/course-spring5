package org.iproduct.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.exception.InvalidEntityIdException;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService service;

//    @InitBinder()
//    protected void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) throws IllegalArgumentException{
//                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            }
//
//            @Override
//            public String getAsText() throws IllegalArgumentException {
//                return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDateTime) getValue());
//            }
//        });
//    }

    @GetMapping
    public List<Article> getArticles() {
        return service.getArticles();
    }

    @GetMapping("{id}")
    public Article getArticles(@PathVariable String id) {
        return service.getArticleById(id);
    }

    @DeleteMapping("{id}")
    public Article deleteArticles(@PathVariable String id) {
        return service.deleteArticle(id);
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
//                                                 UriComponentsBuilder uriComponentsBuilder, HttpServletRequest req) {
        Article created = service.createArticle(article);
        URI location = MvcUriComponentsBuilder.fromMethodName(ArticleController.class, "createArticle", Article.class)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
//        URI location = uriComponentsBuilder.path(req.getServletPath())
//                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
        log.info("Article created: {}", location);
        return ResponseEntity.created(location).body(created);
//        return ResponseEntity.status(303).location(location).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<Article> addArticle(@PathVariable String id, @RequestBody Article article) {
        if(!article.getId().equals(id)) throw new InvalidEntityIdException(
                String.format("Article ID=%s from path is different from Entity ID=%s", id, article.getId()));
        Article old = service.getArticleById(article.getId());
        Article updated = service.updateArticle(old.getAuthorId(), article);
        log.info("Article updated: {}", updated);
        return ResponseEntity.ok(updated);
    }

}