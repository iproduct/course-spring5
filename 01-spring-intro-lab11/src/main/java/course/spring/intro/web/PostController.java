package course.spring.intro.web;

import course.spring.intro.dao.PostRepository;
import course.spring.intro.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping
    List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
