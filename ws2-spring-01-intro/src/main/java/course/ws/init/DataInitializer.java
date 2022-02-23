package course.ws.init;

import course.ws.entity.Article;
import course.ws.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    public static final List<Article> SAMPLE_ARTICLES = List.of(
            new Article("Intro to Spring", "Spring MVC is easy ...",
                    "Trayan Iliev", Set.of("spring", "mvc", "boot", "intro")),
            new Article("Hibernate Performance", "Hibernate provides powerful ORM implementation ...",
                    "Georgi Petrov", Set.of("hibernate", "performance")),
            new Article( "Spring Boot is Easy", "Spring Boot makes bootstrapping new Spring projects easy ...",
                    "Trayan Iliev", Set.of("spring", "boot", "intro"))
    );

    private ArticleService articleService;

    @Autowired
    public DataInitializer(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(articleService.count() == 0) {
            log.info("Sample articles created: {}",
                    SAMPLE_ARTICLES.stream().map(articleService::create).collect(Collectors.toList()));
        }
    }
}
