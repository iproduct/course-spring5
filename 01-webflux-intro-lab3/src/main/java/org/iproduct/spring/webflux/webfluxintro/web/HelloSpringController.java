package org.iproduct.spring.webflux.webfluxintro.web;

import org.iproduct.spring.webflux.webfluxintro.model.Article;
import org.iproduct.spring.webflux.webfluxintro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloSpringController {
    @Autowired
    private ArticleService service;


    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Spring!!!";
    }

//    @GetMapping("/api/articles")
//    public List<Article> getArticles() {
//        return service.getArticles();
//    }

}
