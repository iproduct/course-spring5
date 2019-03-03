package org.iproduct.spring.webmvc.web;

import org.iproduct.spring.webmvc.model.Article;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice()
public class ArticlesAdvice {
    @ModelAttribute("article")
    Article getArticleModelAttribute() {
        return new Article();
    }
}
