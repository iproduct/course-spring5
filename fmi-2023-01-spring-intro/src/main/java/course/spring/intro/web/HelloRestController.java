package course.spring.intro.web;

import course.spring.intro.dao.ArticleRepositoryInMemory;
import course.spring.intro.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
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
        return new Article("Spring Intro", "Spring is developer friendly web service platform", "T. Iliev");
    }

}
