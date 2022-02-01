package course.spring.intro.web;

import course.spring.intro.entity.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
        LocalDateTime now = LocalDateTime.now();
        return new Article(1L, "Intro to Spring", "Spring MVC is easy ...",
                "Trayan Iliev", Set.of("spring", "mvc", "intro"), now, now);
    }
}
