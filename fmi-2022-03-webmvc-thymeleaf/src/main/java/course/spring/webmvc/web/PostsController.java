package course.spring.webmvc.web;

import course.spring.webmvc.entity.Post;
import course.spring.webmvc.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

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
}
