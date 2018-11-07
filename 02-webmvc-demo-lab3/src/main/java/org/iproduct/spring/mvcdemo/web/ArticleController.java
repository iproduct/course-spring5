package org.iproduct.spring.mvcdemo.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.mvcdemo.domain.ArticleService;
import org.iproduct.spring.mvcdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/")
public class ArticleController {
    @Autowired
    ArticleService service;

    @GetMapping("/list")
    ModelAndView getArticles() {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("articles", service.getAllArticles());
        return modelAndView;
    }

    @GetMapping("/add")
    String showArticleForm (@ModelAttribute Article article) {
        return"add";
    }

    @PostMapping(value = "/submit-article")
    public String addArticle(@ModelAttribute("article") Article article,
                             ModelMap model) {

        log.info("Article Submitted: " + article);
        service.add(article);
//        if(!file.isEmpty() && file.getOriginalFilename().length() > 0) {
////            handleMultipartFile(file);
////            article.setPictureUrl(file.getOriginalFilename());
////        }
////        repository.create(article);
        return "redirect:/list";
    }
}
