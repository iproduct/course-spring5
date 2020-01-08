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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        User user = (User) auth.getPrincipal();
        model.addAttribute("path", "articles");
        model.addAttribute("articles", articlesService.findAll());
        log.debug("GET Articles: {}",  articlesService.findAll());
        return "articles";
    }

    @GetMapping("/article-form")
    public ModelAndView getArticleForm(
            @ModelAttribute("article") Article article,
            @RequestParam(value="mode", required=false) String mode,
            @RequestParam(value="articleId", required=false) String articleId
    ) {
        String title = "Add New Article";
        if("edit".equals(mode)) {
            // TODO get article to edit
            title = "Edit Article";
        }
        ModelAndView result = new ModelAndView("article-form");
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
            Authentication auth) {
        if(errors.hasErrors()) {
            model.addAttribute("fileError", null);
            return "article-form";
        } else {
            if(!file.isEmpty() && file.getOriginalFilename().length() > 0) {
                if(Pattern.matches(".+\\.(png|jpg|jpeg)", file.getOriginalFilename())) {
                    handleMultipartFile(file);
                    article.setPictureUrl(file.getOriginalFilename());
                } else {
                    model.addAttribute("fileError", "Submit PNG or JPG picture please!");
                    return "article-form";
                }
            }
            User author = (User)auth.getPrincipal();
            log.info("!!!!!! Logged User: {}", author);
            if(article.getId() == null) {  // create
                log.info("Create new article: {}", article);
                article.setAuthor(author);
                articlesService.add(article);
            } else { //edit
                log.info("Edit article: {}", article);
                articlesService.update(article);
            }
            return "redirect:/articles";
        }
    }

    private void handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
        log.info("File: " + name + ", Size: " + size);
        String path = name;
        try {
            File currentDir = new File(uploadsDir);
            if(!currentDir.exists()) {
                currentDir.mkdirs();
            }
            path = currentDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            path = new File(path).getAbsolutePath();
            log.info(path);
            File f = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
        } catch (IOException ex) {
            log.error("Error coping file: {} [{}]",  path, file.getSize());
        }
    }

}
