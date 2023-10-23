package course.spring.intro.init;

import course.spring.intro.dao.ArticleRepositoryInMemory;
import course.spring.intro.dao.ArticleRepositoryJpa;
import course.spring.intro.model.Article;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private ArticleRepositoryJpa repo;

    public DataInitializer(ArticleRepositoryJpa repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            repo.save(new Article("Spring Intro", "Spring is developer friendly web service platform", "T. Iliev"));
            repo.save(new Article("Spring Data JPA", "Spring Data JPA allows easy RDBMS persis1tence.", "T. Iliev", Set.of("spring data", "jpa")));
        }
    }
}
