package course.spring.restmvc.web;

import course.spring.restmvc.dao.PostsRepository;
import course.spring.restmvc.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsResource {
    @Autowired
    private PostsRepository postsRepo;

    @GetMapping
    public List<Post> getAllPosts() {
        return postsRepo.findAll();
    }

    @PostMapping
    public Post getAllPosts(@RequestBody Post post) {
        return postsRepo.create(post);
    }
}
