package org.iproduct.spring.webmvcdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Controller
public class GreetingController {

    @Autowired
    private ArticleProvider provider;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="Stranger") String name, Model model) {
        model.addAttribute("articles", provider.getArticles());
        model.addAttribute("name", name);

        return "mygreeting";
    }

}