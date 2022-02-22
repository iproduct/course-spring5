package course.spring.intro.web;

import course.spring.intro.entity.Article;
import course.spring.intro.exception.InvalidEntityDataException;
import course.spring.intro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAll();
    }

    @GetMapping("/{id:[\\d]+}")
    public Article getArticle(@PathVariable("id") Long id) {
        return articleService.getById(id);
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article created = articleService.create(article);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public Article updateArticle(@RequestBody Article article, @PathVariable("id") Long id) {
        if(!id.equals(article.getId())){
            throw new InvalidEntityDataException(
                    String.format("ID in URI: '%d' id different from ID in body: '%d'", id, article.getId()));
        }
        return articleService.update(article);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public Article updateArticle(@PathVariable("id") Long id) {
        return articleService.deleteById(id);
    }

}
