package course.spring.restmvc.web;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostsResource {
    @Autowired
    private PostRepository postsRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") String id) {
        return postsRepository.findById(id).orElseThrow(() ->
                new NonexistingEntityException(String.format("Post with ID:%s does not exist.", id)));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post created = postsRepository.insert(post);
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
        return postsRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable String id) {
        Post removed = getPostById(id);
        postsRepository.deleteById(id);
        return removed;
    }

}
