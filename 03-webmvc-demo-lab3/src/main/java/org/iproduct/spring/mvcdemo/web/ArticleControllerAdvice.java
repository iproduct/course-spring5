package org.iproduct.spring.mvcdemo.web;

import org.iproduct.spring.mvcdemo.model.Article;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ArticleControllerAdvice {
    @ModelAttribute("article")
    Article getArticleModelAttribute() {
        return new Article();
    }
}
