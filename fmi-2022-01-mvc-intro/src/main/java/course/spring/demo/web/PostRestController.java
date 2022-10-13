package course.spring.demo.web;

import course.spring.demo.dao.PostRepository;
import course.spring.demo.entity.Post;
import course.spring.demo.exception.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    @Autowired
    private PostRepository postsRepo;

    @GetMapping
    public List<Post> getAllPosts() {
        return postsRepo.findAll();
    }
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postsRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Post with ID=" + id +" does not exist"));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> addNewPost(@RequestBody Post post) {
        var created =  postsRepo.save(post);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

}
