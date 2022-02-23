package course.ws.blogs.web;

import course.ws.blogs.entity.Article;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<Article> addArticle(@Valid @RequestBody Article article, Errors errors) {
        if(errors.hasErrors()) {
            List<String> fieldViolations = errors.getFieldErrors().stream()
                    .map(err -> String.format("Invalid field value: %s='%s' : %s",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            List<String> violations = errors.getGlobalErrors().stream()
                    .map(err -> err.getObjectName() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            violations.addAll(fieldViolations);
            throw new InvalidEntityDataException("Invalid article data", violations);
        }
        Article created = articleService.create(article);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public Article updateArticle(@RequestBody Article article, Errors errors, @PathVariable("id") Long id) {
        if (!id.equals(article.getId())) {
            throw new InvalidEntityDataException(
                    String.format("ID in URI: '%d' id different from ID in body: '%d'", id, article.getId()));
        }
        if (errors.hasErrors()) {
            List<String> fieldViolations = errors.getFieldErrors().stream()
                    .map(err -> String.format("Invalid field value: %s='%s' : %s",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            List<String> violations = errors.getGlobalErrors().stream()
                    .map(err -> err.getObjectName() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            violations.addAll(fieldViolations);
            throw new InvalidEntityDataException("Invalid article data", violations);
        }
        return articleService.update(article);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public Article updateArticle(@PathVariable("id") Long id) {
        return articleService.deleteById(id);
    }

}
