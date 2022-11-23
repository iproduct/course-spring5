package course.spring.webmvc.web;

import course.spring.webmvc.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/posts")
public class PostsController {
    private PostsService postsService;

    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping
    public ModelAndView getArticles() {
        var modelAndView = new ModelAndView("posts");
        modelAndView.addObject("posts", postsService.getAllPosts());
        return modelAndView;
    }
}
