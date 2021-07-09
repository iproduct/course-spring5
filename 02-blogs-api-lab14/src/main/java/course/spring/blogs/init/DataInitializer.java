package course.spring.blogs.init;

import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    public static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Dependency Injection", "Should I use DI or service lookup pattern? ...", 5L),
            new Post("Apring AOP", "AOP is easy ...", 5L),
            new Post("Spring Beans and Wiring", "There are several ways to configure Spring beans ...", 5L)
    );

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        if(postRepository.count() == 0) {
            log.info("Creating sample posts: {}", SAMPLE_POSTS);
            SAMPLE_POSTS.forEach(postRepository::create);
        }
        log.info("Posts available: {}", postRepository.findAll());
    }
}
