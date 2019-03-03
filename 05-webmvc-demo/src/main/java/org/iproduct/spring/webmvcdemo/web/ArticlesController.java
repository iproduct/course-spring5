package org.iproduct.spring.webmvcdemo.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.webmvcdemo.domain.ArticlesService;
import org.iproduct.spring.webmvcdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping
    public String getArticles(Model model){
        model.addAttribute("title", "articles");
        model.addAttribute("articles", articlesService.getArticles());
        log.info("GET Articles: " + articlesService.getArticles());
        return "articles";
    }

    @GetMapping("/article-form")
    public String getArticleForm(@ModelAttribute ("article") Article article, ModelMap model){
        model.addAttribute("title", "article-form");
        return  "article-form";
    }

    @PostMapping("/submit-article")
    public String addArticle(@Valid @ModelAttribute ("article") Article article,
                             BindingResult errors,
                             @RequestParam(name = "cancel", required = false) String cancelBtn,
                             Model model) {
        if(cancelBtn != null) return "redirect:/articles";
        if(errors.hasErrors()) {
            List<String> errorMessages = errors.getAllErrors().stream().map(err -> {
                ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
                return String.format("Error in '%s' - invalid value: '%s'", cv.getPropertyPath(), cv.getInvalidValue());
            }).collect(Collectors.toList());
            model.addAttribute("myErrors", errorMessages);
            return "article-form";
        }
        else {
            log.info("POST Article: " + article);
            articlesService.add(article);
            return "redirect:/articles";
        }
    }
}
