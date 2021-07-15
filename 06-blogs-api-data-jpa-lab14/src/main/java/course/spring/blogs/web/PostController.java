package course.spring.blogs.web;

import course.spring.blogs.entity.Post;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

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
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post created = postService.addPost(post);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().
                        pathSegment("{id}").buildAndExpand(created.getId()).toUri())
                .body(created);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        if(! id.equals(post.getId())) {
            throw new InvalidEntityDataException(String.format("Post ID=%d is different from URL ID=%d",
                    post.getId(), id));
        }
        return postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable Long id) {
        return postService.deletePostById(id);
    }

}
