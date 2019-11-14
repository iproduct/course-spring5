package course.spring.restmvc.web;

import course.spring.restmvc.domain.ArticlesService;
import course.spring.restmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService service;

    @GetMapping
    public List<Article> getArticles() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity addArticle(@RequestBody Article article,
                                     UriComponentsBuilder uriBuilder) {
        Article created = service.add(article);
        return ResponseEntity.created(
                uriBuilder.pathSegment("{id}").build(created.getId()))
                    .body(created);
    }
}
