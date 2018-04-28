package org.iproduct.spring.webmvc.web;

import org.iproduct.spring.webmvc.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ArticleConroller {

    @Autowired
    private ArticleProvider provider;



    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public String getArticles(
        @RequestParam(name="name", required = false, defaultValue = "Stranger") String name,
        Model model) {
        model.addAttribute("name", name);
        model.addAttribute("articles", provider.getArticles());
        return "articles";
    }
}
