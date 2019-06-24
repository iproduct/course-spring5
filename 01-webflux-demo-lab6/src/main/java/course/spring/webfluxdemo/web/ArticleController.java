package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ArticleService;
import course.spring.webfluxdemo.exception.InvalidEntityDataException;
import course.spring.webfluxdemo.exception.NonexisitngEntityException;
import course.spring.webfluxdemo.model.Article;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @GetMapping
    public Collection<Article> getAllArticles() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public Article getArticleById(@PathVariable("id") String id)
            throws NonexisitngEntityException {
        return service.getById(id);
    }

    @PostMapping
    public Article addArticle(@RequestBody Article article) {
        return service.create(article);
    }

    @PutMapping("{id}")
    public Article updateArticle(@PathVariable("id") String id, @RequestBody Article article)
            throws NonexisitngEntityException, InvalidEntityDataException {
        if(!id.equals(article.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Entity ID:%s differs from resource URL ID:%s", article.getId(), id));
        }
        return service.update(article);
    }

    @DeleteMapping("{id}")
    public Article deleteArticle(@PathVariable("id") String id)
            throws NonexisitngEntityException {
        return service.deleteById(id);
    }
}
