package course.spring.intro.web;

import course.spring.intro.entity.Article;
import course.spring.intro.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
public class ArticleController {
    private ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @GetMapping
    public ModelAndView getArticles() {
        var articles = articleService.getAllArticles();
        log.info("articles count: {}", articles.size());
        return new ModelAndView("articles", Map.of("articles", articles));
    }

    @GetMapping("/article-form")
    public String addArticle(@ModelAttribute("article") Article article, Model model) {
        log.info("add article: {}", article);
        model.addAttribute("title", "Add Post");
        return "article-form";
    }
}
