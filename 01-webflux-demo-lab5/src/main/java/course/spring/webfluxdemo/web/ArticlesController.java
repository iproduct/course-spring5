package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ArticlesService;
import course.spring.webfluxdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Article updateArticle(@PathVariable("id") String id,
                          @RequestBody Article article){
        return null;
    }



}
