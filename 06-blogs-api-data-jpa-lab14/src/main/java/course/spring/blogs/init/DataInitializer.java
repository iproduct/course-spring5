package course.spring.blogs.init;

import course.spring.blogs.entity.Post;
import course.spring.blogs.entity.User;
import course.spring.blogs.service.PostService;
import course.spring.blogs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    public static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "admin", "admin@mycompany.com"),
            new User("Default", "User", "user", "user", "user@mycompany.com")
    );

    public static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Dependency Injection", "Should I use DI or service lookup pattern? ..."),
            new Post("Apring AOP", "AOP is easy ..."),
            new Post("Spring Beans and Wiring", "There are several ways to configure Spring beans ...")
    );

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.getUsersCount() == 0) {
            log.info("!!! Creating sample users: {}", SAMPLE_USERS);
            SAMPLE_USERS.forEach(userService::addUser);
        }
        List<User> users = userService.getAllUsers();
        log.info("Users available: {}", users);
        if (postService.getPostsCount() == 0) {
            log.info("!!! Creating sample posts: {}", SAMPLE_POSTS);
            SAMPLE_POSTS.forEach(post -> {
                post.setAuthor(users.get(0));
                postService.addPost(post);
            });
        }
        log.info("Posts available: {}", postService.getAllPosts());
    }
}
