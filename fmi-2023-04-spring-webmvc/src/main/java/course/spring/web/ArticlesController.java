package course.spring.web;

import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.RepoBacked;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ArticlesController {
    @Inject
    @Qualifier("repoProvider")
    @RepoBacked
    private ArticleProvider articleProvider;

    @GetMapping
    public String getHome(){
        return "home";
    }
    @GetMapping("/articles")
    public String getArticles(Model model){
        model.addAttribute("articles", articleProvider.getArticles());
        return "articles";
    }
}
