package course.spring.intro.web;

import course.spring.intro.dto.ErrorResponse;
import course.spring.intro.dto.PostCreateDto;
import course.spring.intro.dto.mapper.DtoMapper;
import course.spring.intro.entity.Post;
import course.spring.intro.exception.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    static final List<Post> DEFAULT_POSTS = List.of(
        new Post(1L, "New in Spring 6", "API versions updated.", Set.of("spring 6", "novelty")),
        new Post(2L, "Hibernate API Intrinsics", "Hibernate provides many important customization options.",
                Set.of("hibernate", "jpa", "spring data jpa")),
        new Post(3L, "Spring AI Integration", "Spring Boot provides OLLAMA starter.",
                Set.of("spring", "spring boot", "ollama", "ai"))
    );
    static AtomicLong nextId = new AtomicLong(3);
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

    @PostMapping
//    @ResponseStatus(CREATED)
    public ResponseEntity<Post> create(@RequestBody PostCreateDto postDto) {
        var post = DtoMapper.mapPostCreateDtoToPost(postDto);
        post.setId(nextId.incrementAndGet());
        posts.put(post.getId(), post);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                    .buildAndExpand(post.getId()).toUri())
                .body(post);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handelEntityNotFoundException(EntityNotFoundException ex){
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND.value(), ex.getMessage()));
    }


}
