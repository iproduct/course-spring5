package org.iproduct.spring.webmvcdemo.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.webmvcdemo.domain.ArticlesService;
import org.iproduct.spring.webmvcdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticlesController {
    private static final String UPLOADS_DIR = "tmp";

    @Autowired
    private ArticlesService articlesService;

    @GetMapping
    public String getArticles(Model model){
        model.addAttribute("path", "articles");
        model.addAttribute("articles", articlesService.getArticles());
        log.debug("GET Articles: " + articlesService.getArticles());
        return "articles";
    }

    @PostMapping(params = "edit")
    public String editArticle(@RequestParam("edit") String editId, Model model){
        log.info("Editing article: " + editId);
        return "redirect:/articles/article-form?mode=edit&articleId=" + editId;
    }

    @PostMapping(params = "delete")
    public String deleteArticle(@RequestParam("delete") String deleteId, Model model){
        log.info("Deleting article: " + deleteId);
        articlesService.delete(deleteId);
        return "redirect:/articles";
    }

    @GetMapping("/article-form")
    public String getArticleForm(@ModelAttribute ("article") Article article, ModelMap model,
                                @RequestParam(value="mode", required=false) String mode,
                                @RequestParam(value="articleId", required=false) String articleId){
        String title = "Add New Article";
        if("edit".equals(mode)) {
             Optional<Article> foundArticle = articlesService.getArticleById(articleId);
             if(foundArticle.isPresent()) {
                 article = foundArticle.get();
                 model.addAttribute("article", article);
                 title = "Edit Article";
             }
        }

        model.addAttribute("path", "article-form");
        model.addAttribute("title", title);
        return  "article-form";
    }

    @PostMapping("/article-form")
    public String addArticle(@Valid @ModelAttribute ("article") Article article,
                             BindingResult errors,
//                             @RequestParam(name = "cancel", required = false) String cancelBtn,
                             @RequestParam("file") MultipartFile file,
                             Model model) {
//        if(cancelBtn != null) return "redirect:/articles";
        if(errors.hasErrors()) {
            List<String> errorMessages = errors.getAllErrors().stream().map(err -> {
                ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
                return String.format("Error in '%s' - invalid value: '%s'", cv.getPropertyPath(), cv.getInvalidValue());
            }).collect(Collectors.toList());
            model.addAttribute("myErrors", errorMessages);
            model.addAttribute("fileError", null);
            return "article-form";
        } else {
            log.info("POST Article: " + article);
            if (!file.isEmpty() && file.getOriginalFilename().length() > 0) {
                if (Pattern.matches("\\w+\\.(jpg|png)", file.getOriginalFilename())) {
                    handleMultipartFile(file);
                    article.setPictureUrl(file.getOriginalFilename());
                } else {
                    model.addAttribute("myErrors", null);
                    model.addAttribute("fileError", "Submit picture [.jpg, .png]");
                    return "article-form";
                }
            }
            if (article.getId() == null) {
                log.info("ADD New Article: " + article);
                articlesService.add(article);
            } else {
                article.setModified(LocalDateTime.now());
                log.info("UPDATE Article: " + article);
                articlesService.update(article);
            }
            return "redirect:/articles";
        }
    }

    private void handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
        log.info("File: " + name + ", Size: " + size);
        try {
            File currentDir = new File(UPLOADS_DIR);
            if(!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            path = new File(path).getAbsolutePath();
            log.info(path);
            File f = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
