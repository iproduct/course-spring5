package course.spring.webmvc.web;

import course.spring.webmvc.domain.ArticlesService;
import course.spring.webmvc.exception.NonexistingEntityException;
import course.spring.webmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/article-form")
    public String getArticleForm(@ModelAttribute("article") Article article, ModelMap model,
            @RequestParam(value = "mode", required = false) String mode,
             @RequestParam(value = "articleId", required = false) String articleId) throws NonexistingEntityException {
        model.addAttribute("path", "articles/article-form");
        String title = "lbl.add.article";
        if(mode!=null && mode.equals("edit") && articleId != null) {
            Optional<Article> edited = articlesService.getArticleById(articleId);
            Article art = edited.orElseThrow(
                    () -> new NonexistingEntityException(
                            String.format("Article with ID=%s does not exist.", articleId)));
            model.put("article", art);
            title = "lbl.edit.article";
        }
        model.put("title", title);
        return "article-form";
    }


}
