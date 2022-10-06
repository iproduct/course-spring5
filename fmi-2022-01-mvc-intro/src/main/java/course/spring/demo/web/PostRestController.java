package course.spring.demo.web;

import course.spring.demo.entity.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    public static  final List<Post> posts = List.of(
            new Post(1L, "Novelties in Spring 5", "WEbFlux is new reactive web API in Spring 5 ..."),
            new Post(2L, "Spring Data is Easy", "Spring Data generates your queris automatically ..."),
            new Post(3L, "Spring Boot", "Spring Boot allows easy configuration of your project ...")
    );
    @GetMapping
    public List<Post> getAllPosts() {
        return posts;
    }
}
