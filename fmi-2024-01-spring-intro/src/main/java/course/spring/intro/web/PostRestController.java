package course.spring.intro.web;

import course.spring.intro.entity.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    @GetMapping
    public List<Post> getPosts() {
        return List.of(
                new Post(1L, "New in Spring 6", "API versions updated."),
                new Post(2L, "Hibernate API Intrinsics", "Hibernate provides many important customization options."),
                new Post(3L, "Spring AI Integration", "Spring Boot provides OLLAMA starter.")
        );
    }
}
