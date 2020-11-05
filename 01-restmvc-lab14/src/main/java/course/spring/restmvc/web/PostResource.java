package course.spring.restmvc.web;

import course.spring.restmvc.dto.PostDto;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

//import static course.spring.restmvc.util.DtoUtil.getPostDto;
import static course.spring.restmvc.util.ErrorHandlingUtil.handleErrors;

@RestController
@RequestMapping("/api/posts")
public class PostResource {
    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts().stream()
                .map(post -> mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") Long id) {
        return mapper.map(postService.getPostById(id), PostDto.class);
    }

    @PostMapping
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody PostDto postDto, Errors errors, Principal principal) {
        handleErrors("Validation errors creating post", errors);
        Post created = postService.addPostDto(postDto);
        PostDto resultDto = mapper.map(created, PostDto.class);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(resultDto);
    }


    @PutMapping("/{id}")
    public PostDto updatePost(@PathVariable("id") Long id, @Valid @RequestBody PostDto postDto, Errors errors) {
        handleErrors("Validation errors updating post", errors);
        Post found = postService.getPostById(id);
        if(!found.getId().equals(postDto.getId())) {         // or simpler alternative - use ResponseStatusException
           throw new InvalidEntityDataException(
                    String.format("Post ID:%d is different from resource URL ID: %d.", postDto.getId(), id));
        }
        return mapper.map(postService.updatePost(postDto), PostDto.class);
    }

    @DeleteMapping("/{id}")
    public PostDto deletePost(@PathVariable("id") Long id) {
         return mapper.map(postService.deletePost(id), PostDto.class);
    }


}
