package course.spring.web;

import course.spring.exception.FileUploadException;
import course.spring.model.Article;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.RepoBacked;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
@Slf4j
public class ArticlesController {
    @Inject
    @Qualifier("repoProvider")
    @RepoBacked
    private ArticleProvider articleProvider;

    @Value("${uploads.directory}")
    private String uploadsDir;
    @Value("${uploads.path_prefix}")
    private String uploadsPath;

    @GetMapping
    public String getHome(Model model){
        model.addAttribute("path", "/");
        return "home";
    }
    @GetMapping("/articles")
    public String getArticles(Model model){
        model.addAttribute("path", "/articles");
        model.addAttribute("articles", articleProvider.getArticles());
        return "articles";
    }

    @GetMapping("/articles/add")
    public String addArticle(@ModelAttribute("article") Article article, Model model){
        model.addAttribute("path", "/articles/add");
        return "article-form";
    }

    @PostMapping("/articles/add")
    public String addArticle(
            @Valid @ModelAttribute ("article") Article article,
            BindingResult errors,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model) {
//            ,@NotNull
//            Authentication auth) {
//        User author = (User)auth.getPrincipal();
        model.addAttribute("fileError", null);
        if(file != null && !file.isEmpty() && file.getOriginalFilename().length() > 4) {
            if (Pattern.matches(".+\\.(png|jpg|jpeg)", file.getOriginalFilename())) {
                try {
                    handleFile(file, article);
                } catch (FileUploadException ex) {
                    model.addAttribute("fileError", ex.getMessage());
                    return "article-form";
                }
            } else {
                model.addAttribute("fileError", "Submit PNG or JPG picture please!");
                return "article-form";
            }
        }
//        if(author == null) {
//            errors.addError(new ObjectError("article", "No authenticated author"));
//            return "article-form";
//        }
        if(errors.hasErrors()) {
            return "article-form";
        }
        if(article.getId() == null) {  // create
            log.info("Create new article: {}", article);
            articleProvider.createArticle(article);
        }
//        } else { //edit
//            log.info("Edit article: {}", article);
//            articleProvider.(article);
//        }
        return "redirect:/articles";
    }

    private void handleFile(MultipartFile file, Article article) {
        String oldName = article.getImageUrl();
        if(oldName != null && oldName.length() > 0) { //delete old image file
            Path oldPath = Paths.get(uploadsDir, oldName).toAbsolutePath();
            log.info("Deleting path: {}", oldPath);
            if (Files.exists(oldPath)) {
                try {
                    Files.delete(oldPath);
                } catch (IOException ex) {
                    log.error("Error deleting file: {}", oldPath);
                }
            }
        }
        if(file != null && file.getOriginalFilename().length() > 4) {
            String newName = file.getOriginalFilename();
            Path newPath = Paths.get(uploadsDir, newName).toAbsolutePath();
            int n = 0;
            String finalName = newName;
            while (Files.exists(newPath)) {   // change destination file name if it already exists
                finalName = newName.substring(0, newName.length()-4) + "_" + ++n + newName.substring(newName.length()-4);
                newPath = Paths.get(uploadsDir, finalName).toAbsolutePath();
            }
            try {
                log.info("Saving new file: '{}', size: {}", newPath, file.getSize());
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(newPath));
                article.setImageUrl(uploadsPath + "/" + finalName);
            } catch (IOException ex) {
                log.error("Error coping file: " + newPath + "[" + file.getSize() + "]", ex);
                throw new FileUploadException("Error uploading file: " + file.getOriginalFilename());
            }
        }
    }

}
