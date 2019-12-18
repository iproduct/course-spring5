package course.spring.webmvc.web;

import course.spring.webmvc.domain.ArticlesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping
    public String getArticles(Model model) {
        model.addAttribute("path", "articles");
        model.addAttribute("articles", articlesService.findAll());
        log.debug("GET Articles: {}",  articlesService.findAll());
        return "articles";
    }
}
