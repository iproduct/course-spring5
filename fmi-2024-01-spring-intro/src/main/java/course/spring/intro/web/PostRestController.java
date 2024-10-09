package course.spring.intro.web;

import course.spring.intro.dao.PostRepository;
import course.spring.intro.dto.ErrorResponse;
import course.spring.intro.dto.PostCreateDto;
import course.spring.intro.dto.mapper.DtoMapper;
import course.spring.intro.entity.Post;
import course.spring.intro.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private PostRepository postRepo;
//    static final List<Post> DEFAULT_POSTS = List.of(
//        new Post(1L, "New in Spring 6", "API versions updated.", Set.of("spring 6", "novelty")),
//        new Post(2L, "Hibernate API Intrinsics", "Hibernate provides many important customization options.",
//                Set.of("hibernate", "jpa", "spring data jpa")),
//        new Post(3L, "Spring AI Integration", "Spring Boot provides OLLAMA starter.",
//                Set.of("spring", "spring boot", "ollama", "ai"))
//    );
//    static AtomicLong nextId = new AtomicLong(3);
//    private Map<Long, Post> posts = new ConcurrentHashMap<>();

//    public PostRestController() {
//        DEFAULT_POSTS.stream().forEach(post -> posts.put(post.getId(), post));
//    }

    @GetMapping
    public List<Post> getPosts() {
        return postRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post getPostById(@PathVariable(name="id") Long id) {
        return postRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Post with id=%d not found", id))
        );
    }

    @PostMapping
//    @ResponseStatus(CREATED)
    public ResponseEntity<Post> create(@RequestBody PostCreateDto postDto) {
        var post = DtoMapper.mapPostCreateDtoToPost(postDto);
        post = postRepo.save(post);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                    .buildAndExpand(post.getId()).toUri())
                .body(post);

    }

    @PutMapping(path = "/{id}")
    public Post update(@RequestBody Post post, @PathVariable(name="id") Long id) {
        var removed = postRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Post with id=%d not found", id))
        );
        post = postRepo.save(post);
        return post;
    }

    @DeleteMapping(path = "/{id}")
    public Post update(@PathVariable(name="id") Long id) {
        var removed = postRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Post with id=%d not found", id))
        );
        postRepo.deleteById(id);
        return removed;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handelEntityNotFoundException(EntityNotFoundException ex){
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND.value(), ex.getMessage()));
    }

}
