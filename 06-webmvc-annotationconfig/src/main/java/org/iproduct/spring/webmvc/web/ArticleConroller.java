package org.iproduct.spring.webmvc.web;

import org.iproduct.spring.webmvc.model.Article;
import org.iproduct.spring.webmvc.service.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ArticleConroller {

    @Autowired
    private ArticleRepository repository;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }



    @GetMapping("/")
    public String getIndex() {
        return "redirect:articles";
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public String getArticles(
            @RequestParam(name="name", required = false, defaultValue = "Stranger") String name,
            Model model) {
        model.addAttribute("name", name);
        model.addAttribute("articles", repository.findAll());
        return "articles";
    }

    @GetMapping("/new-article")
    public ModelAndView showForm() {
        return new ModelAndView("articleForm", "article", new Article());
    }

    @PostMapping(value = "/submit-article")
    public String addArticle(@ModelAttribute("article") Article article, BindingResult result) {
        if (result.hasErrors()) {
            return "articleForm";
        }
        repository.create(article);
        return "redirect:articles";
    }



}
