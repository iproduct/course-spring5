package course.spring.intro.web;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.dto.ErrorResponse;
import course.spring.intro.entity.Article;
import course.spring.intro.exception.EntityNotFoundException;
import course.spring.intro.exception.InvalidEntityDataException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static course.spring.intro.utils.ErrorHandlingUtils.handleValidationErrors;

//@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {
//    @Autowired
    ArticleRepository articleRepository;

    @GetMapping
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @GetMapping("{id}")
    public Article getArticlesById(@PathVariable("id") Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article with id " + id + " not found"));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Article> addArticle(@Valid @RequestBody Article article, Errors errors) {
        handleValidationErrors(errors);
        var created = articleRepository.save(article);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Collections.EMPTY_LIST));
    }

   @ExceptionHandler
    public ResponseEntity<?> handleEntityNotFoundException(InvalidEntityDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getConstraintViolations()));
    }
}
