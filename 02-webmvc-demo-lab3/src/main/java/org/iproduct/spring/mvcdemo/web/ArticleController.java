package org.iproduct.spring.mvcdemo.web;

import org.iproduct.spring.mvcdemo.domain.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ArticleController {
    @Autowired
    ArticleService service;

    @GetMapping
    ModelAndView getArticles() {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("articles", service.getAllArticles());
        return modelAndView;
    }
}
