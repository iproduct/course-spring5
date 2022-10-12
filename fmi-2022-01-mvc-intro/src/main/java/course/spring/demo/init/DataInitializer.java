package course.spring.demo.init;

import course.spring.demo.dao.PostRepository;
import course.spring.demo.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    public static  final List<Post> SAMPLE_POSTS = List.of(
            new Post("Novelties in Spring 5", "WebFlux is new reactive web API in Spring 5 ...",
                    Set.of("spring", "novelty", "webflux")),
            new Post( "Spring Data is Easy", "Spring Data generates your queris automatically ...",
                    Set.of("spring data", "spring", "intro")),
            new Post( "Spring Boot", "Spring Boot allows easy configuration of your project ...")
    );

    @Autowired
    private PostRepository postsRepo;

    @Override
    public void run(String... args) throws Exception {
        if(postsRepo.count() == 0) {
            postsRepo.saveAll(SAMPLE_POSTS);
        }
    }
}
