package course.spring.webmvc.web;

import course.spring.webmvc.domain.ArticlesService;
import course.spring.webmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping
    public String getArticles(Model model) {
        List<Article> articles = articlesService.getArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("path", "articles");
        return "articles";
    }


}
