package course.spring.intro.web;

import course.spring.intro.entity.Article;
import course.spring.intro.service.ArticleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public String actionsArticle(@RequestParam(value = "edit", required = false) Long edit,
                                       @RequestParam(value="delete", required = false) Long delete,
                                       UriComponentsBuilder uriComponentsBuilder) {
        log.info("actionsArticle edit: {}, delete {}", edit, delete);
        if (edit != null ) {
            URI uri = uriComponentsBuilder.path("/article-form")
                    .query("mode=edit&articleId={id}").buildAndExpand(edit).toUri();
            return "redirect:" + uri.toString();
        }
        if (delete != null ) {
            articleService.deleteArticleById(delete);
        }
        return "redirect:/";
    }

    @GetMapping("/article-form")
    public String addArticle(@ModelAttribute("article") Article article,
                             @RequestParam(value = "mode", required = false) String mode,
                             @RequestParam(value = "articleId", required = false) Long articleId,
                             Model model) {
        log.info("show article form: {}", article);
        if ("edit".equals(mode)) {
            model.addAttribute("article", articleService.getArticleById(articleId));
        }
        model.addAttribute("title", "edit".equals(mode)? "Edit Post" : "Add Post");
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
