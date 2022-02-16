package course.ws.web;

import course.ws.entity.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/hello")
public class HelloController {
    @GetMapping
    public String sayHello() {
        return "Hello from Spring MVC!";
    }

    @GetMapping("/article")
    public Article getArticle() {
        return new Article(1L, "Intro to Spring", "Spring MVC is easy ...",
                "Trayan Iliev", Set.of("spring", "mvc", "boot", "inro"));
    }
}
