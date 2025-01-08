package course.spring.blog.init;

import course.spring.blog.entity.Post;
import course.spring.blog.entity.Role;
import course.spring.blog.entity.User;
import course.spring.blog.service.PostService;
import course.spring.blog.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
@Profile("!test")
public class DataInitializer implements ApplicationRunner {
    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "admin123", Role.ADMIN),
            new User("Default", "Author", "author", "admin123", Role.AUTHOR),
            new User("Default", "Reader", "reader", "reader123", Role.READER)
    );
    private static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Spring Data JPA Intro",
                    "Spring Data JPA is easy ...", "/uploads/robot-reading-information.jpg",
                    SAMPLE_USERS.get(1), Set.of("spring", "mvc", "boot", "intro")),
            new Post("Spring Data JPA and Hibernate",
                    "Hibernate provides powerful ORM implementation ...", "/uploads/robot-reading-information.jpg",
                    SAMPLE_USERS.get(1), Set.of("hibernate", "performance")),
            new Post( "Spring Data Integrations",
                    "Spring Data provides reactive db integrations for a number of databases ...",
                    "/uploads/robot-reading-information.jpg",
                    SAMPLE_USERS.get(1), Set.of("spring", "boot", "intro"))
    );


    private PostService postService;
    private UserService userService;

    @Autowired
    public DataInitializer(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public void run(ApplicationArguments args) throws Exception {
        if(userService.getUsersCount() == 0) {
            SAMPLE_USERS.forEach(userService::create);
            log.info("Sample users created: {}", userService.getAllUsers());
        }
        if(postService.getPostsCount() == 0) {
            try {
                var defaultAuthor = userService.getUserByUsername("admin");
                SAMPLE_POSTS.forEach(art -> art.setAuthor(defaultAuthor));
                defaultAuthor.getPosts().addAll(SAMPLE_POSTS);
                SAMPLE_POSTS.forEach(postService::addPost);
                log.info("Sample articles created: {}", postService.getAllPosts());
            } catch (EntityNotFoundException ex){
                log.warn("Error creating sample articles: Dafault author could not be found.");
            } catch (Exception ex) {
                log.error("Error creating sample articles:", ex);
            }
        }
    }
}
