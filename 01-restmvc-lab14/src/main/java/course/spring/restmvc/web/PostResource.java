package course.spring.restmvc.web;

import course.spring.restmvc.dto.PostDTO;
import course.spring.restmvc.dto.PostUserDTO;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.User;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.service.PostService;
import course.spring.restmvc.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.print.attribute.standard.Destination;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.restmvc.util.DtoUtil.getPostDTO;
import static course.spring.restmvc.util.ErrorHandlingUtil.handleErrors;

@RestController
@RequestMapping("/api/posts")
public class PostResource {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @GetMapping
    public Collection<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<PostDTO> addPost(@Valid @RequestBody PostDTO post, Errors errors, Principal principal) {
        handleErrors("Validation errors creating post", errors);
        Post created = postService.addPostDto(post);
        PostDTO postDto = getPostDTO(created);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(postDto);
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
