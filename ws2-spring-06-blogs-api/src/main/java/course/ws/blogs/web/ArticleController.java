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
    public List<Article>  getAllArticles() {
       return articleService.findAllArticles();
    }

    @GetMapping("/{id:\\d+}")
    public Article  getArticleById(@PathVariable Long id) {
       return articleService.findArticleById(id);
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Article> addNewArticle(@Valid @RequestBody Article article, Errors errors) {
        if (errors.hasErrors()) {
            List<String> filedViolations = errors.getFieldErrors().stream()
                    .map(err ->
                            String.format("%s = '%s': %s", err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            List<String> violations = errors.getGlobalErrors().stream()
                    .map(err -> err.toString()).collect(Collectors.toList());
            violations.addAll(filedViolations);
            throw new InvalidEntityDataException("Invalid article data", violations);
        }
        Article newArticle = articleService.create(article);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                                .buildAndExpand(newArticle.getId()).toUri())
                .body(newArticle);
    }

    @PutMapping("/{id:\\d+}")
    public Article updateArticle(@RequestBody Article article, @PathVariable Long id) {
        if(!id.equals(article.getId())){
            throw new InvalidEntityDataException(
                    String.format("ID='%s' in path differs from ID='%s' in message body", id, article.getId()));
        }
        return articleService.update(article);
    }

    @DeleteMapping("/{id:\\d+}")
    public Article  deleteArticleById(@PathVariable Long id) {
        return articleService.deleteArticleById(id);
    }

}
