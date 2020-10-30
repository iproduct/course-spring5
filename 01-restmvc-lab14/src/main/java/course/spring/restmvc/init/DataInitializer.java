package course.spring.restmvc.init;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PostRepository postRepo;
    @Override
    public void run(String... args) throws Exception {
        if(postRepo.count() == 0) {
            List.of(
               new Post("New in Spring", "Spring 5 is here ...", "Trayan Iliev", Set.of("spring", "java", "webflux")),
               new Post("DI in Spring", "Many ways to do it ...", "Trayan Iliev", Set.of("spring", "di")),
               new Post("Reactive Web Programmiing", "Webflux uses Reactor ...", "Trayan Iliev", Set.of("spring", "reactor", "webflux")),
               new Post("JPA Made Easy", "Spring Data DSL automates JPQL creation ...", "Trayan Iliev", Set.of("spring", "spring data", "jpql"))
            ).forEach(postRepo::save);
        }
    }
}
