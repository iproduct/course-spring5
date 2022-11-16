package course.spring.webmvc.init;

import course.spring.webmvc.dao.PostRepository;
import course.spring.webmvc.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    public static  final List<Post> SAMPLE_POSTS = List.of(
            new Post("Novelties in Spring 5", "WebFlux is new reactive web API in Spring 5 ...",
                    "https://www.publicdomainpictures.net/pictures/270000/velka/water-drop-on-leaf-1531756513Ogt.jpg",
                    Set.of("spring", "novelty", "webflux")),
            new Post( "Spring Data is Easy", "Spring Data generates your queris automatically ...",
                    "https://www.publicdomainpictures.net/pictures/370000/velka/kunstliche-intelligenz-1603866343eG3.jpg",
                    Set.of("spring data", "spring", "intro")),
            new Post( "Spring Boot", "Spring Boot allows easy configuration of your project ...",
                    "https://www.publicdomainpictures.net/pictures/390000/velka/artificial-intelligence-1612992481fj2.jpg")
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
