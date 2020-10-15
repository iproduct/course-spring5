package course.spring.restmvc.web;

import course.spring.restmvc.dao.PostsRepository;
import course.spring.restmvc.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/posts")
public class PostsResource {
    @Autowired
    private PostsRepository postsRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postsRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Post createPost(@RequestBody Post post) {
        return postsRepository.create(post);
    }
}
