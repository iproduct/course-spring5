package org.iproduct.spring.webmvc.web;

import org.iproduct.spring.webmvc.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private ArticleProvider provider;

    @GetMapping("/articles")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="Anonimous") String name,
            Model model) {
        model.addAttribute("articles", provider.getArticles());

        model.addAttribute("name", name);

        return "mygreeting";
    }

    @PostMapping("/rm1")
    public String rm1(Model model, RedirectAttributes rm) {
        System.out.println("Entered rm1 method ");

        rm.addFlashAttribute("modelkey", "modelvalue");
        rm.addAttribute("nonflash", "nonflashvalue");
        model.addAttribute("modelkey2", "modelvalue2");

        return "redirect:/rm2";
    }


    @GetMapping("/rm2")
    public String rm2(Model model, HttpServletRequest request) {
        System.out.println("Entered rm2 method ");

        Map md = model.asMap();
        for (Object modelKey : md.keySet()) {
            Object modelValue = md.get(modelKey);
            System.out.println(modelKey + " -- " + modelValue);
        }

        System.out.println("=== Request data ===");

        java.util.Enumeration<String> reqEnum = request.getParameterNames();
        while (reqEnum.hasMoreElements()) {
            String s = reqEnum.nextElement();
            System.out.println(s);
            System.out.println("==" + request.getParameter(s));
        }

        return "mygreeting";
    }
}
