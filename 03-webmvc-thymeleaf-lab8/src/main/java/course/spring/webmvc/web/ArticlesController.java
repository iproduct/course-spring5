package course.spring.webmvc.web;

import course.spring.webmvc.domain.ArticlesService;
import course.spring.webmvc.model.Article;
import course.spring.webmvc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @Value("${uploads.directory}")
    private String uploadsDir;

    @GetMapping
    public String getArticles(Model model, Authentication auth) {
        model.addAttribute("path", "articles");
        model.addAttribute("articles", articlesService.findAll());
        log.debug("GET Articles: {}",  articlesService.findAll());
        return "articles";
    }

    @PostMapping(params = "edit")
    public String editArticle(@RequestParam("edit") String editId, Model model, UriComponentsBuilder uriBuilder){
        log.info("Editing article: " + editId);
        URI uri = uriBuilder.path("/articles/article-form")
                .query("mode=edit&articleId={id}").buildAndExpand(editId).toUri();
        return "redirect:" + uri.toString();
    }

    @PostMapping(params = "delete")
    public String deleteArticle(@RequestParam("delete") String deleteId, Model model){
        log.info("Deleting article: " + deleteId);
        Article old = articlesService.remove(deleteId);
        handleFile(null, old);
        return "redirect:/articles";
    }


    @GetMapping("/article-form")
    public ModelAndView getArticleForm(
            @ModelAttribute("article") Article article,
            @RequestParam(value="mode", required=false) String mode,
            @RequestParam(value="articleId", required=false) String articleId
    ) {
        ModelAndView result = new ModelAndView("article-form");
        String title = "Add New Article";
        if("edit".equals(mode)) {
            Article found = articlesService.findById(articleId);
            result.addObject("article", found);
            title = "Edit Article";
        }

        result.addObject("title", title);
        result.addObject("path", MvcUriComponentsBuilder.fromMethodName(
                ArticlesController.class,"getArticleForm", new Article(), "", "")
                    .build().getPath());
        return result;
    }

    @PostMapping("/article-form")
    public String addArticle(
            @Valid @ModelAttribute ("article") Article article,
            BindingResult errors,
            @RequestParam("file") MultipartFile file,
            Model model,
            @NotNull
            Authentication auth) {
        User author = (User)auth.getPrincipal();
        model.addAttribute("fileError", null);
        if(!file.isEmpty() && file.getOriginalFilename().length() > 4) {
            if (Pattern.matches(".+\\.(png|jpg|jpeg)", file.getOriginalFilename())) {
                handleFile(file, article);
            } else {
                model.addAttribute("fileError", "Submit PNG or JPG picture please!");
                return "article-form";
            }
        }
        if(author == null) {
            errors.addError(new ObjectError("article", "No authenticated author"));
            return "article-form";
        }
        if(errors.hasErrors()) {
            return "article-form";
        }
        article.setAuthorId(author.getId());
        article.setAuthorName(author.getFullName());
        if(article.getId() == null) {  // create
            log.info("Create new article: {}", article);
            articlesService.add(article);
        } else { //edit
            log.info("Edit article: {}", article);
            articlesService.update(article);
        }
        return "redirect:/articles";
    }

    private void handleFile(MultipartFile file, Article article) {
        String oldName = article.getPictureUrl();
        if(oldName != null && oldName.length() > 0) { //delete old image file
            Path oldPath = Paths.get(getUploadsDir(), oldName).toAbsolutePath();
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
            Path newPath = Paths.get(getUploadsDir(), newName).toAbsolutePath();
            int n = 0;
            String finalName = newName;
            while (Files.exists(newPath)) {   // change destination file name if it already exists
                finalName = newName.substring(0, newName.length()-4) + "_" + ++n + newName.substring(newName.length()-4);
                newPath = Paths.get(getUploadsDir(), finalName).toAbsolutePath();
            }
            try {
                log.info("Saving new file: '{}', size: {}", newPath, file.getSize());
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(newPath));
                article.setPictureUrl(finalName);
            } catch (IOException ex) {
                log.error("Error coping file: {} [{}]", newPath, file.getSize());
            }
        }
    }

    protected String getUploadsDir() {
        File uploadsDir = new File(this.uploadsDir);
        if(!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }
        return uploadsDir.getAbsolutePath();
    }

}
