package course.spring.intro.web;

import course.spring.intro.dao.PostRepository;
import course.spring.intro.model.ErrorResposnse;
import course.spring.intro.model.Post;
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
    private PostRepository postRepository;

    @GetMapping
    List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity getAllPosts(@PathVariable("id") String id) {
        Post found = postRepository.findById(id);
        if(found != null) {
            return ResponseEntity.ok(found);
        } else {
            return ResponseEntity.status(404).body(
                new ErrorResposnse(404, String.format("Post with ID: %s not found.", id)));
        }
    }

    @PostMapping
    ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post created = postRepository.create(post);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    Post updatePost(@PathVariable("id") String id) {
        return null; // TODO implement it
    }

    @DeleteMapping("/{id}")
    Post deletePost(@PathVariable("id") String id) {
        return null; // TODO implement it
    }
}
