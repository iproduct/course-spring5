package course.spring.blogs.init;

import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.Post;
import course.spring.blogs.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    public static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "admin", "admin@mycompany.com"),
            new User("Default", "User", "user", "user", "user@mycompany.com")
    );

    public static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Dependency Injection", "Should I use DI or service lookup pattern? ...", 5L),
            new Post("Apring AOP", "AOP is easy ...", 5L),
            new Post("Spring Beans and Wiring", "There are several ways to configure Spring beans ...", 5L)
    );

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("!!! Creating sample users: {}", SAMPLE_USERS);
            SAMPLE_USERS.forEach(userRepository::create);
        }
        List<User> users = userRepository.findAll();
        log.info("Users available: {}", users);
        if (postRepository.count() == 0) {
            log.info("!!! Creating sample posts: {}", SAMPLE_POSTS);
            SAMPLE_POSTS.forEach(post -> {
                post.setAuthorId(users.get(0).getId());
                postRepository.create(post);
            });
        }
        log.info("Posts available: {}", postRepository.findAll());
    }
}
