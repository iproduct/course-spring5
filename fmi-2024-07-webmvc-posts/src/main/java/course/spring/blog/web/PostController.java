package course.spring.blog.web;

import course.spring.blog.entity.Post;
import course.spring.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/posts")
@Log
public class PostController {
    public static final String UPLOADS_DIR = "tmp";
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ModelAndView getPosts() {
        var modelAndView = new ModelAndView("posts");
        modelAndView.addObject("posts", postService.getAllPosts());
        return modelAndView;
    }

    @GetMapping("/add")
    public String getPostForm(@ModelAttribute("post") Post article, Model model) {
        model.addAttribute("path", "/posts/add");
        model.addAttribute("title", "Add Post");

        return "post-form";
    }

    @PostMapping("/add")
    public String addPost(@Valid @ModelAttribute("post") Post post, Errors errors,
                          @RequestParam("file") MultipartFile file, Model model) {
        log.warning("Invalid post data: " + errors.toString());
        if (errors.hasErrors()) {
            return "post-form";
        }
        if (!file.isEmpty() && file.getOriginalFilename().length() > 0) {
            if (Pattern.matches(".+\\.(jpg|png)", file.getOriginalFilename())) {
                if (handleMultipartFile(file)) {
                    post.setImageUrl("uploads/" + file.getOriginalFilename());
                } else {
                    model.addAttribute("fileError", "Error saving image: "+ file.getOriginalFilename());
                }
            } else {
                model.addAttribute("fileError", "Submit picture in [.jpeg | .png] format");
            }
        }
        if (post.getId() == null) {  // create
            log.info("Create new post:" + post);
            postService.addPost(post);
        }
        return "redirect:/posts";
    }

    private boolean handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
        log.info("File: " + name + ", size: " + size);
        try {
            File currentDir = new File(UPLOADS_DIR);
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            var imageFile = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(imageFile));
            return true;
        } catch (IOException e) {
            log.severe("Error saving image file: " + e.getMessage());
            return false;
        }

    }

}
