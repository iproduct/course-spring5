package course.spring.blogs.web;

import course.spring.blogs.entity.Post;
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

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post created = postService.addPost(post);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().
                    pathSegment("{id}").buildAndExpand(created.getId()).toUri())
            .body(created);
    }
}
