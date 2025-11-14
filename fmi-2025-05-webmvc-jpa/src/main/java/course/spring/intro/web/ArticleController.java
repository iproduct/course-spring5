package course.spring.intro.web;

import course.spring.intro.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class ArticleController {
    private ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @GetMapping
    public String getArticles(Model model) {
        var articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);
        log.info("articles count: {}", articles.size());
        return "articles";
    }
}
