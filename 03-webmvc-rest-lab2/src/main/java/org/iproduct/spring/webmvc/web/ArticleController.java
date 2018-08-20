package org.iproduct.spring.webmvc.web;

import org.iproduct.spring.webmvc.model.Article;
import org.iproduct.spring.webmvc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    ArticleService service;

    @GetMapping
    public List<Article> getArticles() {
        return service.getArticles();
    }
}
