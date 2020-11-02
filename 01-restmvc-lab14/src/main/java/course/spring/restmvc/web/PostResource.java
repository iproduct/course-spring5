package course.spring.restmvc.web;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/posts")
public class PostResource {
    @Autowired
    private PostRepository postRepo;
    @GetMapping
    public Collection<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Long id) {
//        return postRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                String.format("Post with ID:%s not found.", id)));
        return postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Post with ID:%s not found.", id)));
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post created = postRepo.save(post);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        Post found = postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Post with ID:%s not found.", id)));
        if(!found.getId().equals(post.getId())) {
           throw new InvalidEntityDataException(
                    String.format("Post ID:%d is different from resource URL ID: %d.", post.getId(), id));
        }
        return postRepo.save(post);
    }

    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable("id") Long id) {
         Post deleted = postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                 String.format("Post with ID:%s not found.", id)));
         postRepo.deleteById(id);
         return deleted;
    }


}
