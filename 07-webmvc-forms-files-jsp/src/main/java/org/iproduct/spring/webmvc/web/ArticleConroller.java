package org.iproduct.spring.webmvc.web;

import org.iproduct.spring.webmvc.exceptions.CustomValidationException;
import org.iproduct.spring.webmvc.model.Article;
import org.iproduct.spring.webmvc.model.ArticlesSelection;
import org.iproduct.spring.webmvc.service.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
@SessionAttributes({"selection"})
public class ArticleConroller {

    private static final String UPLOADS_DIR = "tmp";

    @Autowired
    private ArticleRepository repository;

    private static final Logger LOG = LoggerFactory.getLogger(ArticleConroller.class.getName());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ModelAttribute("selection")
    ArticlesSelection getArticleModelAttribute() {
        return new ArticlesSelection();
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
    public String showArticleForm() {
//        return new ModelAndView("articleForm", "article", new Article());
        return "articleForm";
    }

    @GetMapping("/files")
    public String showFilesForm() {
        return "fileUploadForm";
    }

    @PostMapping(value = "/submit-article")
    public String addArticle(@Valid @ModelAttribute("article") Article article,
                             BindingResult result, ModelMap model,
                             @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "articleForm";
        }

        if(!file.isEmpty() && file.getOriginalFilename().length() > 0) {
            handleMultipartFile(file);
            article.setPictureUrl(file.getOriginalFilename());
        }
        repository.create(article);
        return "redirect:articles";
    }

    @PostMapping(value = "/select-articles")
    public String addArticle(@ModelAttribute("selection") ArticlesSelection selection,
                             BindingResult result, ModelMap model,
                             SessionStatus session,
                             @RequestParam(value="clear", required = false) String clear) {
        if (result.hasErrors()) {
            return "articles";
        }
        if(clear != null) {
            session.setComplete();
            selection.setArticleIds(new ArrayList<>());
        }
        System.out.println("Selection:" + selection);
        model.addAttribute("articles", repository.findAll());
        return "articles";
    }

    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public String submit(@RequestParam("files") MultipartFile[] files, ModelMap modelMap) {
        modelMap.addAttribute("files", files);
        for(MultipartFile file: files) {
            handleMultipartFile(file);
        }
        return "fileUploadView";
    }

    private void handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
        System.out.println("File: " + name + ", Size: " + size);
        try {
            File currentDir = new File(UPLOADS_DIR);
            if(!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            path = new File(path).getAbsolutePath();
            System.out.println(path);
            File f = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @ExceptionHandler ({CustomValidationException.class, FileSystemException.class})
    @Order(1)
    public ResponseEntity<String> handle(Exception ex) {
        LOG.error("Article Controller Error:",ex);
        return ResponseEntity.badRequest().body(ex.toString());
    }


}
