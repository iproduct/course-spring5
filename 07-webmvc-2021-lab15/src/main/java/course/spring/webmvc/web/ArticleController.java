package course.spring.webmvc.web;

import course.spring.webmvc.domain.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String getArticles(Model model){
        model.addAttribute("articles", articleService.findAll());
        return "articles";
    }
}
