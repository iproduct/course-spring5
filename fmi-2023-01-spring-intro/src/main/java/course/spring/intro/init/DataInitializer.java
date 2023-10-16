package course.spring.intro.init;

import course.spring.intro.dao.ArticleRepositoryInMemory;
import course.spring.intro.model.Article;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private ArticleRepositoryInMemory repo;

    public DataInitializer(ArticleRepositoryInMemory repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        repo.create(new Article("Spring Intro", "Spring is developer friendly web service platform", "T. Iliev"));
        repo.create(new Article("Spring Data JPA", "Spring Data JPA allows easy RDBMS persistence.","T. Iliev", Set.of("spring data", "jpa")));
    }
}
