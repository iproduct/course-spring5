package course.spring.restmvc.web;

import course.spring.restmvc.domain.ArticlesService;
import course.spring.restmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping
    List<Article> getArticles(){
        return articlesService.findAll();
    }
    @PostMapping
    ResponseEntity<Article> addArticle(@RequestBody Article article, UriComponentsBuilder uriBuilder) {
        Article created = articlesService.add(article);
        return ResponseEntity.created(uriBuilder.pathSegment("{id}").build(created.getId())).body(created);
    }
}
