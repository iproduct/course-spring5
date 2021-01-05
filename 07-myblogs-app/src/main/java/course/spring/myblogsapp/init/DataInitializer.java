package course.spring.myblogsapp.init;

import course.spring.myblogsapp.entity.Post;
import course.spring.myblogsapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    public static final List<Post> posts = List.of(
            new Post("New in Spring 5", "WebFlux is here and is haigh performace...",
                    "https://p2.piqsels.com/preview/639/504/10/branch-engine-leaves-spring.jpg",
                    "admin", Set.of("Spring", "WebFlux")),
            new Post("DI in Spring", "Many ways to do it, but what are advantages ...",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRGq5nnPNS-nVHQWbwmKNtxhVs7hZkTD_VuYA&usqp=CAU",
                    "admin", Set.of("Spring", "dependency injection", "DI")),
            new Post("Autowiring", "To autowire or not to autowire ...",
                    "https://www.publicdomainpictures.net/pictures/280000/nahled/electrical-wiring.jpg",
                    "admin", Set.of("Spring", "Autowire"))
    );

    @Autowired
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        if(postService.getPostsCount() == 0) {
            posts.forEach(postService::addPost);
        }
    }
}
