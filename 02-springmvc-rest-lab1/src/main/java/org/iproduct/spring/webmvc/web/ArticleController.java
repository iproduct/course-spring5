package org.iproduct.spring.webmvc.web;

import com.sun.deploy.security.BlacklistedCerts;
import org.iproduct.spring.webmvc.exception.EntityNotFoundException;
import org.iproduct.spring.webmvc.model.Article;
import org.iproduct.spring.webmvc.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/articles")
public class ArticleController {
    @Autowired
    private ArticleProvider provider;

    @GetMapping
    public List<Article> getArticles() {
        return provider.getArticles();
    }

    @GetMapping("{id}")
    public Article getArticleById(@PathVariable("id") String id) {
        return provider.getArticleById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(UriComponentsBuilder uriComponentBuilder, @RequestBody Article article) {
        Article created = provider.addArticle(article);
        UriComponents uri = uriComponentBuilder.path("api/articles/{id}").buildAndExpand(created.getId());
        return ResponseEntity.created(uri.toUri()).body(created);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity handleExceptions(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
