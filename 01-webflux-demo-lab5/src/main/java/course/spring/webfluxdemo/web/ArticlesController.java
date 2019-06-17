package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ArticlesService;
import course.spring.webfluxdemo.exception.IllegalEntityBodyException;
import course.spring.webfluxdemo.exception.NonexistingEntityException;
import course.spring.webfluxdemo.model.Article;
import course.spring.webfluxdemo.model.HttpErrorResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("api/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping
    public Collection<Article> getAllArticles(){
        return articlesService.getAll();
    }

    @PostMapping
    public Article addArticle(@RequestBody Article article){
        return articlesService.add(article);
    }

    @PutMapping("{id}")
    public Article updateArticle(@PathVariable("id") String id,
                                        @RequestBody Article article)
            throws NonexistingEntityException, IllegalEntityBodyException {
        if(!id.equals(article.getId())) {
            throw new IllegalEntityBodyException(
                    String.format("ID in body:'%s' different from path:'%s'",
                            article.getId() ,id));
        }
        return articlesService.update(article);
    }

    @DeleteMapping("{id}")
    public Article deleteArticle(@PathVariable("id") String id) throws NonexistingEntityException {
        return articlesService.delete(id);
    }

}
