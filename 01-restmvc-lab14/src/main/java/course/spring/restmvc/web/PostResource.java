package course.spring.restmvc.web;

import course.spring.restmvc.entity.Post;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("/api/posts")
public class PostResource {
    @Autowired
    private PostService postService;
    @GetMapping
    public Collection<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post created = postService.addPost(post);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        Post found = postService.getPostById(id);
        if(!found.getId().equals(post.getId())) {         // or simpler alternative - use ResponseStatusException
           throw new InvalidEntityDataException(
                    String.format("Post ID:%d is different from resource URL ID: %d.", post.getId(), id));
        }
        return postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable("id") Long id) {
         return postService.deletePost(id);
    }


}
