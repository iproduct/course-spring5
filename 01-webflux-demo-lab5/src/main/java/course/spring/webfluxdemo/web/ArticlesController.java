package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ArticlesService;
import course.spring.webfluxdemo.model.Article;
import course.spring.webfluxdemo.model.HttpErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("api/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping
    public List<Article> getAllArticles(){
        return articlesService.getAll();
    }

    @PostMapping
    public Article addArticle(@RequestBody Article article){
        return articlesService.add(article);
    }

    @PutMapping("{id}")
    public ResponseEntity updateArticle(@PathVariable("id") String id,
                                        @RequestBody Article article){
        if(!id.equals(article.getId())) {
            return ResponseEntity.badRequest().body(new HttpErrorResponse(BAD_REQUEST,
                String.format("ID in body:'%s' different from path:'%s'",
                        article.getId() ,id)));
        }
        return ResponseEntity.ok(articlesService.update(article));
    }



}
