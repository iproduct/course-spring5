package course.spring.intro.web;

import course.spring.intro.entity.Article;
import course.spring.intro.service.ArticleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
        return new ModelAndView("articles", Map.of("articles", articles, "path", "/"));
    }

    @GetMapping("/article-form")
    public String addArticle(@ModelAttribute("article") Article article, Model model) {
        log.info("show article form: {}", article);
        model.addAttribute("title", "Add Post");
        model.addAttribute("path", "/article-form");
        return "article-form";
    }

    @PostMapping("/article-form")
    public String addArticle(@Valid @ModelAttribute("article") Article article, BindingResult error, Model model) {
        if (error.hasErrors()) {
            return "article-form";
        }
        if (article.getId() == null) {
            log.info("ADD article: {}", article);
            articleService.addArticle(article);
        } else {
            log.info("UPDATE article: {}", article);
            articleService.updateArticle(article);
        }
        model.addAttribute("path", "/");
        return "redirect:/";
    }
}
