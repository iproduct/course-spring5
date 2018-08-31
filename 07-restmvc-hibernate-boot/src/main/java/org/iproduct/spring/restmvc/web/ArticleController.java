package org.iproduct.spring.restmvc.web;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.exception.InvalidEntityIdException;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.model.Views;
import org.iproduct.spring.restmvc.service.ArticleService;
import org.iproduct.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @GetMapping
    @JsonView(Views.Article.class)
    public Collection<Article> getArticles() {
        return articleService.getArticles();
    }

    @GetMapping("{id}")
    @JsonView(Views.Article.class)
    public Article getArticles(@PathVariable long id) {
        return articleService.getArticleById(id);
    }

    @DeleteMapping("{id}")
    @JsonView(Views.Article.class)
    public Article deleteArticles(@PathVariable long id) {
        return articleService.deleteArticle(id);
    }

    @PostMapping
    @JsonView(Views.Article.class)
    public ResponseEntity<Article> addArticle(@RequestBody Article article, Authentication authentication) {
        User author = userService.getUserByUsername(authentication.getName());
        article.setAuthor(author);
        Article created = articleService.addArticle(article);
        URI location = MvcUriComponentsBuilder.fromMethodName(ArticleController.class, "addArticle", article, authentication)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
        return ResponseEntity.created(location).body(created);
//        return ResponseEntity.status(303).location(location).body(created);
    }

    @PutMapping("{id}")
    @JsonView(Views.Article.class)
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody Article article) {
        if(article.getId() != id) throw new InvalidEntityIdException(
                String.format("Article ID=%s from path is different from Entity ID=%s", id, article.getId()));
        Article updated = articleService.updateArticle(article);
        log.info("Article updated: {}", updated);
        return ResponseEntity.ok(updated);
    }
}
