package course.spring.intro.web;

import course.spring.intro.dao.PostRepository;
import course.spring.intro.exception.EntityNotFoundException;
import course.spring.intro.model.ErrorResposnse;
import course.spring.intro.model.Post;
import course.spring.intro.service.PostService;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAllPosts(@PathVariable("id") String id) {
        Post found = postService.getPostById(id);
        if(found != null) {
            return ResponseEntity.ok(found);
        } else {
            return ResponseEntity.status(404).body(
                new ErrorResposnse(404, String.format("Post with ID: %s not found.", id)));
        }
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post created = postService.createPost(post);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") String id, @RequestBody Post post) {
        return postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable("id") String id) {
        return postService.deletePostById(id);
    }

}
