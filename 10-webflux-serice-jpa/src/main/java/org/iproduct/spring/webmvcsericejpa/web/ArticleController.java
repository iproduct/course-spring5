package org.iproduct.spring.webmvcsericejpa.web;

import org.iproduct.spring.webmvcsericejpa.dao.ArticleDao;
import org.iproduct.spring.webmvcsericejpa.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleDao articleService;

    @GetMapping("/articles")
    public Iterable<Article> getArrticlees(){
        return articleService.findAll();
    }
}
