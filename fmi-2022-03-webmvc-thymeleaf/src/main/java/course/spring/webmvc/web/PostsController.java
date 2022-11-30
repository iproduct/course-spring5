package course.spring.webmvc.web;

import course.spring.webmvc.entity.Post;
import course.spring.webmvc.service.PostsService;
import course.spring.webmvc.util.TagsEditor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/posts")
@Slf4j
public class PostsController {
    private static final String UPLOADS_DIR = "tmp";
    private PostsService postsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, new TagsEditor());
    }


    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping
    public ModelAndView getArticles() {
        var modelAndView = new ModelAndView("posts");
        modelAndView.addObject("posts", postsService.getAllPosts());
        modelAndView.addObject("path", "posts");
        return modelAndView;
    }

    @GetMapping("/post-form")
    public String getArticleForm(@ModelAttribute("post") Post post, ModelMap model,
                                 @RequestParam(value="mode", required=false) String mode,
                                 @RequestParam(value="articleId", required=false) String articleId){

        String title = "Add New Post";
//        if("edit".equals(mode)) {
//            Optional<Post> foundArticle = postsService.getArticleById(articleId);
//            if(foundArticle.isPresent()) {
//                article = foundArticle.get();
//                model.addAttribute("article", article);
//                title = "Edit Article";
//            }
//        }

        model.addAttribute("path", "post-form");
        model.addAttribute("title", title);
        return  "post-form";
    }

    @PostMapping("/post-form")
    public String addArticle(@Valid @ModelAttribute ("post") Post post,
                             BindingResult errors,
                             @RequestParam("file") MultipartFile file,
                             Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("fileError", null);
            return "post-form";
        } else {
            log.info("POST-ing  new Post: " + post);
            if (!file.isEmpty() && file.getOriginalFilename().length() > 0) {
                if (Pattern.matches("\\w+\\.(jpg|png)", file.getOriginalFilename())) {
                    handleMultipartFile(file);
                    post.setImageUrl("uploads/" + file.getOriginalFilename());
                } else {
                    model.addAttribute("fileError", "Submit picture [.jpg, .png]");
                    return "posts-form";
                }
            }
            if (post.getId() == null) {
                log.info("ADD New Article: " + post);
                postsService.add(post);
            } else {
                post.setModified(LocalDateTime.now());
                log.info("UPDATE Article: " + post);
                postsService.update(post);
            }
            return "redirect:/posts";
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
