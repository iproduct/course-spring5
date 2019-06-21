package course.spring.webmvc.web;

import com.sun.jndi.toolkit.url.Uri;
import course.spring.webmvc.domain.ArticlesService;
import course.spring.webmvc.exception.NonexistingEntityException;
import course.spring.webmvc.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticlesController {
    private static final String UPLOADS_DIR = "tmp";

    @Autowired
    private ArticlesService articlesService;

    @GetMapping
    public String getArticles(Model model) {
        List<Article> articles = articlesService.getArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("path", "articles");
        return "articles";
    }

    @PostMapping(params = "edit")
    public String editArticle(@RequestParam("edit") String articleId,
                              UriComponentsBuilder uriBuilder) {
        if(articleId != null) {
            URI uri = uriBuilder.path("/articles/article-form")
                    .query("mode=edit&&articleId={articleId}")
                    .buildAndExpand(articleId).toUri();
            return "redirect:" + uri.toString();
        }
        return "articles";
    }

    @PostMapping(params = "delete")
    public String editArticle(@RequestParam("delete") Long articleId) {
        if(articleId != null) {
           articlesService.delete(articleId);
        }
        return "articles";
    }

    @GetMapping("/article-form")
    public String getArticleForm(@ModelAttribute("article") Article article, ModelMap model,
            @RequestParam(value = "mode", required = false) String mode,
             @RequestParam(value = "articleId", required = false) Long articleId) throws NonexistingEntityException {
        model.addAttribute("path", "articles/article-form");
        String title = "lbl.add.article";
        if(mode!=null && mode.equals("edit") && articleId != null) {
            Optional<Article> edited = articlesService.getArticleById(articleId);
            Article art = edited.orElseThrow(
                    () -> new NonexistingEntityException(
                            String.format("Article with ID=%s does not exist.", articleId)));
            model.put("article", art);
            title = "lbl.edit.article";
        }
        model.addAttribute("title", title);
        return "article-form";
    }

    @PostMapping("/article-form")
    public String addArticle(@Valid @ModelAttribute ("article") Article article,
             BindingResult errors,
//           @RequestParam(name = "cancel", required = false) String cancelBtn,
             @RequestParam("file") MultipartFile file,
             Model model) {
//        if(cancelBtn != null) return "redirect:/articles";
        String title = article.getId() == null ? "lbl.add.article" : "lbl.edit.article";
        model.addAttribute("title", title);
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
                article.setUpdated(LocalDateTime.now());
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
