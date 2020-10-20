package course.spring.intro;

import course.spring.intro.dao.PostRepository;
import course.spring.intro.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PostRepository postRepository;

    private static final List<Post> SAMPLE_POSTS = List.of(
           new Post("New in Spring 5", "WebFlux is here ...", "Trayan Iliev"),
           new Post("Reactive Programming in Spring", "Project Reactor is employed ...", "Trayan Iliev"),
           new Post("Autowiring and DI", "Autowiring is a flexible way to inject components ...", "Dimitar Petrov")
    );

    @Override
    public void run(String... args) throws Exception {
        if(postRepository.count() == 0) {
            SAMPLE_POSTS.forEach(postRepository::create);
        }
    }
}
