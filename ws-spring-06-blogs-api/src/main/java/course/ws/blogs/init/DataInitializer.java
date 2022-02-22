package course.ws.blogs.init;

import course.ws.blogs.entity.Article;
import course.ws.blogs.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    private static final List<Article> SAMPLE_ARTICLES = List.of(
            new Article("Spring Data JPA Intro",
                    "Spring Data JPA is easy ...",
                    "Trayan Iliev", Set.of("spring", "mvc", "boot", "intro")),
            new Article("Spring Data JPA and Hibernate",
                    "Hibernate provides powerful ORM implementation ...",
                    "Georgi Petrov", Set.of("hibernate", "performance")),
            new Article( "Spring Data is Going Reactive",
                    "Spring Data provides reactive db integrations for a number of databases ...",
                    "Trayan Iliev", Set.of("spring", "boot", "intro"))

    );
    private ArticleService articleService;

    @Autowired
    public DataInitializer(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(articleService.getCount() == 0) {
            SAMPLE_ARTICLES.forEach(articleService::create);
            log.info("Sample articles created: {}", articleService.getAll());
        }
    }
}
