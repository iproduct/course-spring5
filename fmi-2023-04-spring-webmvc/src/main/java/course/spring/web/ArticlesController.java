package course.spring.web;

import course.spring.model.Article;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.RepoBacked;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
@Slf4j
public class ArticlesController {
    @Inject
    @Qualifier("repoProvider")
    @RepoBacked
    private ArticleProvider articleProvider;

    @GetMapping
    public String getHome(Model model){
        model.addAttribute("path", "/");
        return "home";
    }
    @GetMapping("/articles")
    public String getArticles(Model model){
        model.addAttribute("path", "/articles");
        model.addAttribute("articles", articleProvider.getArticles());
        return "articles";
    }

    @GetMapping("/articles/add")
    public String addArticle(@ModelAttribute("article") Article article, Model model){
        model.addAttribute("path", "/articles/add");
        return "article-form";
    }

    @PostMapping("/articles/add")
    public String addArticle(
            @Valid @ModelAttribute ("article") Article article,
            BindingResult errors,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model) {
//            ,@NotNull
//            Authentication auth) {
//        User author = (User)auth.getPrincipal();
//        model.addAttribute("fileError", null);
//        if(!file.isEmpty() && file.getOriginalFilename().length() > 4) {
//            if (Pattern.matches(".+\\.(png|jpg|jpeg)", file.getOriginalFilename())) {
//                handleFile(file, article);
//            } else {
//                model.addAttribute("fileError", "Submit PNG or JPG picture please!");
//                return "article-form";
//            }
//        }
//        if(author == null) {
//            errors.addError(new ObjectError("article", "No authenticated author"));
//            return "article-form";
//        }
        if(errors.hasErrors()) {
            return "article-form";
        }
        if(article.getId() == null) {  // create
            log.info("Create new article: {}", article);
            articleProvider.createArticle(article);
        }
//        } else { //edit
//            log.info("Edit article: {}", article);
//            articleProvider.(article);
//        }
        return "redirect:/articles";
    }
}
