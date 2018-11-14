package org.iproduct.spring.mvcdemo.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.mvcdemo.domain.ArticleService;
import org.iproduct.spring.mvcdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    ArticleService service;

    @GetMapping("/articles")
    ModelAndView getArticles() {
        ModelAndView modelAndView = new ModelAndView("aricles");
        modelAndView.addObject("articles", service.getAllArticles());
        return modelAndView;
    }

    @GetMapping("/article-form")
    String showArticleForm (@ModelAttribute Article article) {
        return"article-form";
    }

    @PostMapping(value = "/submit-article")
    public String addArticle(@Valid @ModelAttribute("article") Article article,
                             BindingResult result) {

        log.info("Article Submitted: " + article);

        if(result.hasErrors()) {
            return "article-form";
        } else {
            service.add(article);
            return "redirect:/articles";
        }
//        if(!file.isEmpty() && file.getOriginalFilename().length() > 0) {
//            handleMultipartFile(file);
//            article.setPictureUrl(file.getOriginalFilename());
//        }
//        repository.create(article);
    }
}
