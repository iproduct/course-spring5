package course.spring.restmvc.web;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.ErrorResponse;
import course.spring.restmvc.model.Post;
import course.spring.restmvc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@RestController
@RequestMapping("/api/posts")
public class PostsResource {
    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts(@RequestParam(name="keywords", required = false) String keywordStr) {
        if(keywordStr != null && !keywordStr.trim().isEmpty()) {
            Set<String> keywords = Set.of(keywordStr.trim().split(",\\s*"));
            return postService.getAllPostsByKeywords(keywords);
        } else {
            return postService.getAllPosts();
        }

    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") String id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post created = postService.addPost(post);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post post) {
        if(!id.equals(post.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID:%s differs from body entity ID:%s", id, post.getId()));
        }
        return postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable String id) {
        Post removed = getPostById(id);
        postService.deletePost(id);
        return removed;
    }

}
