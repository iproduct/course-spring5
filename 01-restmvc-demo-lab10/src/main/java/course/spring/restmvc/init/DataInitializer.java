package course.spring.restmvc.init;

import course.spring.restmvc.model.Post;
import course.spring.restmvc.model.Role;
import course.spring.restmvc.model.User;
import course.spring.restmvc.service.PostService;
import course.spring.restmvc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.restmvc.model.Role.*;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Override
    public void run(String... args) throws Exception {
        List<User> defaultUsers = Arrays.asList(new User[]{
                new User("Default", "Admin", "admin@mycompany.com", "admin", "admin",
                        new HashSet(Arrays.asList(new Role[]{ADMIN})),
                        "https://e7.pngegg.com/pngimages/867/694/png-clipart-user-profile-default-computer-icons-network-video-recorder-avatar-cartoon-maker-blue-text.png"),
                new User("Default", "Author", "author@mycompany.com", "author", "author",
                        new HashSet(Arrays.asList(new Role[]{AUTHOR, READER})),
                        "https://www.publicdomainpictures.net/pictures/270000/nahled/man-avatar.jpg"),
        });

        List<Post> defaultPosts = Arrays.asList(new Post[]{
                new Post("New in Spring 5", "WebFlux is here and is haigh performace...", defaultUsers.get(1),
                        Arrays.asList(new String[]{"Spring", "WebFlux"}),
                        "https://p2.piqsels.com/preview/639/504/10/branch-engine-leaves-spring.jpg"),
                new Post("DI in Spring", "Many ways to do it, but what are advantages ...",
                        defaultUsers.get(1),Arrays.asList(new String[]{"Spring", "dependency injection", "DI"}),
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRGq5nnPNS-nVHQWbwmKNtxhVs7hZkTD_VuYA&usqp=CAU"),
                new Post("Autowiring", "To autowire or not to autowire ...", defaultUsers.get(1),
                        Arrays.asList(new String[]{"Spring", "Autowire"}),
                        "https://www.publicdomainpictures.net/pictures/280000/nahled/electrical-wiring.jpg")
        });

        if(userService.count() == 0) {
            defaultUsers.stream().map(userService::createUser)
                .map(user -> user.getId() + ": " + user.getUsername())
                .forEach(log::info);
        }
        if(postService.count() == 0) {
            User defaultAuthor = userService.getUserByUsername("author");
            if(defaultAuthor != null) {
                defaultPosts.forEach(post -> post.setAuthor(defaultAuthor));
                List<Post> createdPosts = defaultPosts.stream().map(postService::createPost).collect(Collectors.toList());
                createdPosts.stream()
                        .map(post -> post.getId() + ": " + post.getTitle())
                        .forEach(log::info);
                defaultAuthor.setPosts(createdPosts);
                userService.updateUser(defaultAuthor);
            }
        }
    }
}
