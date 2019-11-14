package course.spring.restmvc.web;

import course.spring.restmvc.domain.ArticlesService;
import course.spring.restmvc.exception.InvalidEntityException;
import course.spring.restmvc.exception.NonexisitngEntityException;
import course.spring.restmvc.model.Article;
import course.spring.restmvc.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping
    public List<Article> getArticles(){
        return articlesService.findAll();
    }

    @GetMapping("{id}")
    public Article getArticleById(@PathVariable("id") String articleId) {
        return articlesService.findById(articleId);
    }

    @PostMapping
    public ResponseEntity<Article> addArticle(@Valid @RequestBody Article article, Errors errors) {
        Article created = articlesService.add(article);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodName(ArticlesController.class, "addArticle", Article.class)
                    .pathSegment("{id}").build(created.getId()))
                .body(created);
//        2) uriBuilder.path(req.getServletPath()).pathSegment("{id}").build(created.getId()))
//                .body(created);
//        1) ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
//                .body(created);
    }

    @PutMapping("{id}")
    public Article update(@PathVariable String id, @Valid @RequestBody Article article) {
        if(!id.equals(article.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", article.getId(), id));
        }
        return articlesService.update(article);
    }

    @DeleteMapping("{id}")
    public Article remove(@PathVariable String id) {
        return articlesService.remove(id);
    }

//    @ExceptionHandler({NonexisitngEntityException.class, InvalidEntityException.class})
//    public ResponseEntity<ErrorResponse> handleExceptions(RuntimeException ex) {
//        if(ex instanceof NonexisitngEntityException) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
//        }
//    }

}
