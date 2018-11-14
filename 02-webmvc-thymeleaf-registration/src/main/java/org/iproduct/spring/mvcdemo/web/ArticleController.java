package org.iproduct.spring.mvcdemo.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.mvcdemo.domain.ArticleService;
import org.iproduct.spring.mvcdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    ArticleService service;

    @GetMapping("/articles")
    ModelAndView getArticles() {
        ModelAndView modelAndView = new ModelAndView("articles");
        modelAndView.addObject("articles", service.getAllArticles());
        return modelAndView;
    }

    @GetMapping("/article-form")
    String showArticleForm (@ModelAttribute Article article) {
        return"article-form";
    }

    @PostMapping(value = "/submit-article")
    public String addArticle(@ModelAttribute("article") Article article) {

        log.info("Article Submitted: " + article);
        service.add(article);
//        if(!file.isEmpty() && file.getOriginalFilename().length() > 0) {
////            handleMultipartFile(file);
////            article.setPictureUrl(file.getOriginalFilename());
////        }
////        repository.create(article);
        return "redirect:/articles";
    }
}
