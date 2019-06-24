package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ArticleService;
import course.spring.webfluxdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
