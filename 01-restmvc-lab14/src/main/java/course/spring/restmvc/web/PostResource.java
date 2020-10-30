package course.spring.restmvc.web;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
