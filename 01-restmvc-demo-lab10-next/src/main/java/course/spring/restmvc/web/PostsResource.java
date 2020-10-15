package course.spring.restmvc.web;

import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.exception.ValidationErrorsException;
import course.spring.restmvc.model.ErrorResponse;
import course.spring.restmvc.model.Post;
import course.spring.restmvc.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/api/posts")
public class PostsResource {
    @Autowired
    private PostsService postsService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postsService.getAllPosts();
    }

    @GetMapping("{id}")
    public Post getPostById(@PathVariable long id) {
        return postsService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, HttpServletRequest request) {
        Post created = postsService.createPost(post);
        return ResponseEntity.created(
//                ServletUriComponentsBuilder.fromRequest(request).pathSegment("{id}").build(created.getId())
//                UriComponentsBuilder.fromUriString(request.getRequestURL().toString()).pathSegment("{id}").build(created.getId())
//                MvcUriComponentsBuilder.fromMethodName(PostsResource.class, "createPost", post, request)
//                        .pathSegment("{id}").build(created.getId())
                MvcUriComponentsBuilder.fromMethodCall(on(PostsResource.class).createPost(post, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public Post updatePost(@PathVariable Long id, @Valid @RequestBody Post post, Errors errors) {
        if(errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
        if(!id.equals(post.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID:%d differs from body entity ID:%d", id, post.getId()));
        }
        return postsService.updatePost(post);
    }

    @DeleteMapping("{id}")
    public Post deletePost(@PathVariable long id) {
        return postsService.deletePost(id);
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleInvalidEntityData(InvalidEntityDataException ex) {
//        return ResponseEntity.badRequest().body(new ErrorResponse(400, ex.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleNonexisitingEntity(NonexistingEntityException ex) {
//        return ResponseEntity.status(404).body(new ErrorResponse(404, ex.getMessage()));
//    }


}
