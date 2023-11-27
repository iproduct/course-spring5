package course.spring.web;

import course.spring.model.Article;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.RepoBacked;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class AriclesRestController {
    @Inject
    @Qualifier("repoProvider")
    @RepoBacked
    private ArticleProvider articleProvider;


    @GetMapping
    List<Article> getAllArticles() {
        return articleProvider.getArticles();
    }
    @GetMapping("/{id}")
    Article getArticleById(@PathVariable long id) {
        return articleProvider.getArticleById(id);
    }
    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Article> addArticle(@RequestBody Article article) {
        var created = articleProvider.createArticle(article);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }
}
