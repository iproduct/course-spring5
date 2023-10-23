package course.spring.intro.web;

import course.spring.intro.dao.ArticleRepositoryInMemory;
import course.spring.intro.dao.ArticleRepositoryJpa;
import course.spring.intro.model.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {
    @Autowired
    private ArticleRepositoryJpa articleRepository;

    @GetMapping
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleRepository.save(article);
    }

}
