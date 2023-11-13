package course.spring.web;

import course.spring.dao.ArticleRepository;
import course.spring.model.Article;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.Default;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class AriclesController {
    @Inject
//    @Qualifier("defaultProvider")
    @Default
    private ArticleProvider articleProvider;


    @GetMapping
    List<Article> getAllArticles() {
        return articleProvider.getArticles();
    }
}
