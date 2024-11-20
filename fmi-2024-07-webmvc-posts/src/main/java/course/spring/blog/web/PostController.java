package course.spring.blog.web;

import course.spring.blog.entity.Post;
import course.spring.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/posts")
@Log
public class PostController {
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
    public String getPostForm(@ModelAttribute("post") Post article, Model model){
        model.addAttribute("path", "/posts/add");
        return "post-form";
    }

    @PostMapping("/add")
    public String addPost(
            @Valid @ModelAttribute("post") Post post,
            BindingResult errors,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model) {
        model.addAttribute("fileError", null);
        if(errors.hasErrors()) {
            return "post-form";
        }
        if(post.getId() == null) {  // create
            log.info("Create new post:" + post);
            postService.addPost(post);
        }
        return "redirect:/posts";
    }

}
