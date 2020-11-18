package course.spring.restmvc.init;

import course.spring.restmvc.model.Post;
import course.spring.restmvc.model.User;
import course.spring.restmvc.service.PostService;
import course.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.restmvc.model.Role.*;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "admin",
                    "https://lh3.googleusercontent.com/proxy/cGNVbclL0E2LBuUnIxUaC7d-wP_K18xwNUMVzCHmtxgdEtaknGpKCZz-rBVUWP46jCXJSPq6Va7hZ__JZVjG4EKwLx-Kezlk9Qtb5NDc9Gb-E1oq85KV",
                    Set.of(READER, AUTHOR, ADMIN)),
            new User("Default", "Author", "author", "author",
                    "https://cdn.iconscout.com/icon/premium/png-512-thumb/public-domain-user-618551.png",
                    Set.of(AUTHOR)),
            new User("Default", "Reader", "reader", "reader",
                    "https://www.publicdomainpictures.net/pictures/230000/velka/computer-user.jpg",
                    Set.of(READER))
    );
    private static final List<Post> SAMPLE_POSTS = List.of(
            new Post("New in Spring 5", "WebFlux is here ...",
                    "https://www.publicdomainpictures.net/pictures/40000/velka/spring-flowers-13635086725Z1.jpg",
                    List.of("spring", "new")),
            new Post("Reactive Programming in Spring", "Project Reactor is employed ...",
                    "https://www.publicdomainpictures.net/pictures/40000/velka/spring-flowers-13635086725Z1.jpg",
                    List.of("spring", "reactive")),
            new Post("Autowiring and DI", "Autowiring is a flexible way to inject components ...",
                    "https://www.publicdomainpictures.net/pictures/40000/velka/spring-flowers-13635086725Z1.jpg",
                    List.of("spring", "autowiring"))
    );


    @Override
    public void run(String... args) throws Exception {
        if (userService.getCount() == 0) {
            SAMPLE_USERS.forEach(userService::addUser);
        }

        if (postService.getCount() == 0) {
            SAMPLE_POSTS.forEach(post -> {
                post.setAuthorId(userService.getUserByUsername("author").getId());
                postService.addPost(post);
            });
        }
    }
}
