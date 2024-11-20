package course.spring.blog.web;

import course.spring.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    public ModelAndView getPosts() {
        var modelAndView = new ModelAndView("posts");
        modelAndView.addObject("posts", postService.getAllPosts());
        return modelAndView;
    }
}
