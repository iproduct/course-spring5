package course.spring.demo.web;

import course.spring.demo.dao.PostRepository;
import course.spring.demo.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post addNewPost(@RequestBody Post post) {
        return null;
    }

}
