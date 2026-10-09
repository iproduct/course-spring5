package course.spring.intro.web;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.entity.Article;
import course.spring.intro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public List<Article> findAll() {
        return articleService.findAll();
    }

    @PostMapping
    public Article create(@RequestBody Article article) {
        return articleService.create(article);
    }
}
