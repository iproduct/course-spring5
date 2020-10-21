package course.spring.restmvc.web;

import course.spring.restmvc.dao.PostsRepository;
import course.spring.restmvc.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/posts")
public class PostsResource {
    @Autowired
    private PostsRepository postsRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") String id) {
        return postsRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post created = postsRepository.create(post);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
//        if(!id.equals(post.getId())) {
//            throw new InvalidEntityDataException(
//                    String.format("Url ID:%d differs from body entity ID:%d", id, post.getId()));
//        }
        return postsRepository.update(post);
    }

    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable String id) {
        return postsRepository.deleteById(id);
    }

}
