package course.spring.intro.web;

import course.spring.intro.dto.ErrorResponse;
import course.spring.intro.entity.Post;
import course.spring.intro.exception.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    static final List<Post> DEFAULT_POSTS = List.of(
        new Post(1L, "New in Spring 6", "API versions updated."),
        new Post(2L, "Hibernate API Intrinsics", "Hibernate provides many important customization options."),
        new Post(3L, "Spring AI Integration", "Spring Boot provides OLLAMA starter.")
    );
    private Map<Long, Post> posts = new ConcurrentHashMap<>();

    public PostRestController() {
        DEFAULT_POSTS.stream().forEach(post -> posts.put(post.getId(), post));
    }

    @GetMapping
    public List<Post> getPosts() {
        return new ArrayList<>(posts.values());
    }

    @GetMapping(path = "/{pid}")
    public Post getPostById(@PathVariable(name = "pid") Long pid) {
        var post = posts.get(pid);
        if(post == null) {
            throw new EntityNotFoundException(String.format("Post with id=%d not found", pid));
        }
        return post;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handelEntityNotFoundException(EntityNotFoundException ex){
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND.value(), ex.getMessage()));
    }


}
