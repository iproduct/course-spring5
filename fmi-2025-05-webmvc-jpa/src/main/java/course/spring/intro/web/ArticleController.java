package course.spring.intro.web;

import course.spring.intro.entity.Article;
import course.spring.intro.exception.FileSavingException;
import course.spring.intro.service.ArticleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
@Slf4j
public class ArticleController {
    private ArticleService articleService;

    @Value("${uploads.dir}")
    private String uploadDir;

    @Value("${uploads.maxsize}")
    private long maxUploadSize;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ModelAndView getArticles() {
        var articles = articleService.getAllArticles();
        log.info("articles count: {}", articles.size());
        return new ModelAndView("articles", Map.of("articles", articles, "path", "/"));
    }

    @PostMapping
    public String actionsArticle(@RequestParam(value = "edit", required = false) Long edit,
                                 @RequestParam(value = "delete", required = false) Long delete,
                                 UriComponentsBuilder uriComponentsBuilder,
                                 RedirectAttributes redirectAttributes
    ) {
        log.info("actionsArticle edit: {}, delete {}", edit, delete);
        if (edit != null) {
//            redirectAttributes.addAttribute("mode", "edit");
//            redirectAttributes.addAttribute("articleId", edit);
//            return "redirect:/article-form";
            URI uri = uriComponentsBuilder.path("/article-form")
                    .query("mode=edit&articleId={id}").buildAndExpand(edit).toUri();
            return "redirect:" + uri.toString();
        }
        if (delete != null) {
            articleService.deleteArticleById(delete);
        }
        return "redirect:/";
    }

    @GetMapping("/article-form")
    public String addArticle(@ModelAttribute("article") Article article,
                             @RequestParam(value = "mode", required = false) String mode,
                             @RequestParam(value = "articleId", required = false) Long articleId,
                             Model model) {
        log.info("show article form: {}", article);
        if ("edit".equals(mode)) {
            model.addAttribute("article", articleService.getArticleById(articleId));
        }
        model.addAttribute("title", "edit".equals(mode) ? "Edit Post" : "Add Post");
        model.addAttribute("path", "/article-form");
        return "article-form";
    }

    @PostMapping("/article-form")
    public String addArticle(@Valid @ModelAttribute("article") Article article, BindingResult error,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             Model model) {
        if (error.hasErrors()) {
            return "article-form";
        }
        if (!file.isEmpty() && file.getOriginalFilename().length() > 0) {
            var imgFilenamePattern = Pattern.compile(".+\\.(jpe?g|png)");
            var matcher = imgFilenamePattern.matcher(file.getOriginalFilename());
            if (!matcher.matches()) {
                model.addAttribute("fileError", "Submit picture in [.jpeg | .png] format");
                return "article-form";
            }
            var ext = matcher.group(1);
            UUID uuid = UUID.randomUUID();
            var filename = uuid.toString() + "." + ext;
            log.info(filename);
            try {
                handleMultipartFile(file, filename);
                article.setImageUrl("uploads/" + filename);
            } catch (FileSavingException e) {
                model.addAttribute("fileError", e.getMessage());
                return "article-form";
            }
        }
        if (article.getId() == null) {
            log.info("ADD article: {}", article);
            articleService.addArticle(article);
        } else {
            log.info("UPDATE article: {}", article);
            articleService.updateArticle(article);
        }
        model.addAttribute("path", "/");
        return "redirect:/";
    }

    private void handleMultipartFile(MultipartFile file, String filename) {
        long size = file.getSize();
        if (size > maxUploadSize) {
            throw new FileSavingException("File '" + filename + "' [" + size + " bytes] is too large, limit is: " + maxUploadSize);
        }

        log.info("File: {}, size: {}", filename, size);
        try {
            File currentDir = new File(uploadDir);
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + filename;
            var imageFile = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(imageFile));
        } catch (IOException e) {
            log.error("Error saving image file: ", e.getMessage());
            throw new FileSavingException("Error saving image file: " + e.getMessage());
        }
    }
}
