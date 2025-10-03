package course.spring.intro.web;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Article addArticle(@RequestBody Article article) {
        return articleRepository.save(article);
    }
}
