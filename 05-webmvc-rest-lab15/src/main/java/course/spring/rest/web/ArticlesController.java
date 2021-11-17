package course.spring.rest.web;

import course.spring.exception.EntityNotFoundException;
import course.spring.exception.InvalidClientDataException;
import course.spring.rest.dao.ArticleRepository;
import course.spring.rest.model.Article;
import course.spring.rest.model.ErorResposnse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {
    @Autowired
    private ArticleRepository articleRepo;

    @GetMapping
    public List<Article> getArticles() {
        return articleRepo.findAll();
    }

    @GetMapping("/{id}")
    public Article getArticles(@PathVariable String id) {
        return articleRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Entity with ID=%s does not exist.", id)));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article created = articleRepo.insert(article);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .pathSegment("{id}").buildAndExpand(created.getId()).toUri()
//                MvcUriComponentsBuilder.
//                        fromMethodName(ArticlesController.class, "addArticle", Article.class)
//                        .pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable String id, @RequestBody Article article) {
        if(!id.equals(article.getId())) throw new InvalidClientDataException(
                String.format("ID %s from request URL is different from ID %s in request body.", id, article.getId()));
        articleRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Article with ID %s not found.", id)));
        return articleRepo.save(article);
    }

}
