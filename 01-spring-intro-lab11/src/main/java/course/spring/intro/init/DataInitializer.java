package course.spring.intro.init;

import course.spring.intro.dao.PostRepository;
import course.spring.intro.dao.PostRepositoryOld;
import course.spring.intro.dao.UserRepository;
import course.spring.intro.model.Post;
import course.spring.intro.model.Role;
import course.spring.intro.model.User;
import course.spring.intro.service.PostService;
import course.spring.intro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.intro.model.Role.*;

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
            new Post("New in Spring 5", "WebFlux is here ..."),
            new Post("Reactive Programming in Spring", "Project Reactor is employed ..."),
            new Post("Autowiring and DI", "Autowiring is a flexible way to inject components ...")
    );

    @Override
    public void run(String... args) throws Exception {
        if(userService.getCount() == 0) {
            List<User> users = SAMPLE_USERS.stream().map(userService::createUser).collect(Collectors.toList());
        }

        if(postService.getCount() == 0) {
            SAMPLE_POSTS.forEach(post -> {
                post.setAuthorId(userService.getUserByUsername("author").getId());
                postService.createPost(post);
            });
        }
    }
}
