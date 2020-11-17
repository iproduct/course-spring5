package course.spring.intro.web;

import course.spring.intro.exception.InvalidEntityDataException;
import course.spring.intro.model.Post;
import course.spring.intro.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

import static course.spring.intro.util.ErrorHandlingUtils.getErrors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id:^[A-Fa-f0-9]{24}$}")
    public Post getPostById(@PathVariable("id") String id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post, Errors errors) {
        if(errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid Post data: ", getErrors(errors));
        }
        Post created = postService.createPost(post);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id:^[A-Fa-f0-9]{24}$}")
    public Post updatePost( @PathVariable("id") String id,
                            @Valid @RequestBody Post post, Errors errors) {
        if(errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid Post data: ", getErrors(errors));
        }
        if(!id.equals(post.getId())) {
            throw new InvalidEntityDataException("Post ID:%s in the URL differs from ID:%s in the body.");
        }
        return postService.updatePost(post);
    }

    @DeleteMapping("/{id:^[A-Fa-f0-9]{24}$}")
    public Post deletePost(@PathVariable("id") String id) {

        return postService.deletePostById(id);
    }

}
