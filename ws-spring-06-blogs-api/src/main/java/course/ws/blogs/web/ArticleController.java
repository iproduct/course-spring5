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

import static course.ws.blogs.util.ValidationErrorUtil.handleValidationErrors;

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
        handleValidationErrors(errors);
        Article created = articleService.create(article);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public Article updateArticle(@Valid @RequestBody Article article, Errors errors, @PathVariable("id") Long id) {
        if (!id.equals(article.getId())) {
            throw new InvalidEntityDataException(
                    String.format("ID in URI: '%d' id different from ID in body: '%d'", id, article.getId()));
        }
        handleValidationErrors(errors);
        return articleService.update(article);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public Article deleteArticle(@PathVariable("id") Long id) {
        return articleService.deleteById(id);
    }

}
