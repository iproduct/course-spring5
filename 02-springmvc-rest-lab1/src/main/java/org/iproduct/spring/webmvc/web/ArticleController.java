package org.iproduct.spring.webmvc.web;

import com.sun.deploy.security.BlacklistedCerts;
import org.iproduct.spring.webmvc.model.Article;
import org.iproduct.spring.webmvc.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/articles")
public class ArticleController {
    @Autowired
    private ArticleProvider provider;

    @GetMapping
    public List<Article> getArticles() {
        return provider.getArticles();
    }
}
