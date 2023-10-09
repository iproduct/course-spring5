package course.spring.intro.web;

import course.spring.intro.model.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloRestController {
    @GetMapping
    public String sayHello() {
        return "Hello from Spring MVC!";
    }

    @GetMapping("/article")
    public Article getArticle() {
        return new Article();
    }
}
