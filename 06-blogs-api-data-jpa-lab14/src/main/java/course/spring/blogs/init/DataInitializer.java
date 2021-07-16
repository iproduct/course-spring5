package course.spring.blogs.init;

import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.dto.UserCreateDto;
import course.spring.blogs.dto.UserDto;
import course.spring.blogs.entity.Post;
import course.spring.blogs.entity.User;
import course.spring.blogs.service.PostService;
import course.spring.blogs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

import static course.spring.blogs.entity.Role.ADMIN;
import static course.spring.blogs.entity.Role.AUTHOR;

@Component
@Slf4j
@Profile("!test")
public class DataInitializer implements CommandLineRunner {
    public static final List<UserCreateDto> SAMPLE_USERS = List.of(
            new UserCreateDto("Default", "Admin", "admin", "admin123", "admin@mycompany.com", ADMIN),
            new UserCreateDto("Default", "User", "user", "user1234", "user@mycompany.com", AUTHOR)
    );

    public static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Dependency Injection", "Should I use DI or service lookup pattern? ..."),
            new Post("Apring AOP", "AOP is easy ..."),
            new Post("Spring Beans and Wiring", "There are several ways to configure Spring beans ...")
    );

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.getUsersCount() == 0) {
            log.info("!!! Creating sample users: {}", SAMPLE_USERS);
            SAMPLE_USERS.forEach(userService::addUser);
        }
        List<User> users = userRepo.findAll();
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
