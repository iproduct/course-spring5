package course.spring.restmvc.web;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        return postRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Post with ID:%s not found.", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post post) {
        return postRepo.save(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        return postRepo.save(post);
    }

    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable("id") Long id) {
         Post deleted = postRepo.findById(id).orElseThrow();
         postRepo.deleteById(id);
         return deleted;
    }

}
