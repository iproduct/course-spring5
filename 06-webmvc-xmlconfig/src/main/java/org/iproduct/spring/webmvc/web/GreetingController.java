package org.iproduct.spring.webmvc.web;

import org.iproduct.spring.webmvc.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @Autowired
    private ArticleProvider provider;

    @GetMapping("/articles")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="Stranger") String name, Model model) {
        model.addAttribute("articles", provider.getArticles());

        model.addAttribute("name", name);

        return "mygreeting";
    }

}