package course.spring.restjpa.web;

import course.spring.restjpa.exception.InvalidEntityDataException;
import course.spring.restjpa.model.Article;
import course.spring.restjpa.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public List<Article> findAll(){
        return  articleService.findAll();
    }

    @GetMapping("/{id}")
    public Article findById(@PathVariable Long id){
        return  articleService.findById(id);
    }

    @GetMapping(value = "find", params = {"tags"})
    public List<Article> findByTags(@RequestParam String tags){
        return  articleService.findByTags(Arrays.asList(tags.split("%s,%s")));
    }

    @GetMapping(value = "find", params = {"title"})
    public List<Article> findByTitle(@RequestParam String title){
        return  articleService.findByTitle(title);
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Article> findByTitle(@Valid @RequestBody Article article, Errors errors){
        if (errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid article data",
                    errors.getAllErrors().stream().map(e -> e.toString()).collect(Collectors.toList()));
        }
        Article created = articleService.create(article);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId())
                ).body(created);
    }
}
