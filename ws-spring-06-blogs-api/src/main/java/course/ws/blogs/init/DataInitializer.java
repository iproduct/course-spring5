package course.ws.blogs.init;

import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.Role;
import course.ws.blogs.entity.User;
import course.ws.blogs.service.ArticleService;
import course.ws.blogs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@Profile("!test")
public class DataInitializer implements ApplicationRunner {
    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "admin123", Role.ADMIN),
            new User("Default", "Author", "author", "admin123", Role.AUTHOR),
            new User("Default", "Reader", "reader", "reader123", Role.READER)
    );
    private static final List<Article> SAMPLE_ARTICLES = List.of(
            new Article("Spring Data JPA Intro",
                    "Spring Data JPA is easy ...",
                    SAMPLE_USERS.get(1), Set.of("spring", "mvc", "boot", "intro")),
            new Article("Spring Data JPA and Hibernate",
                    "Hibernate provides powerful ORM implementation ...",
                    SAMPLE_USERS.get(1), Set.of("hibernate", "performance")),
            new Article( "Spring Data is Going Reactive",
                    "Spring Data provides reactive db integrations for a number of databases ...",
                    SAMPLE_USERS.get(1), Set.of("spring", "boot", "intro"))

    );


    private ArticleService articleService;
    private UserService userService;

    @Autowired
    public DataInitializer(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public void run(ApplicationArguments args) throws Exception {
        if(userService.getCount() == 0) {
            SAMPLE_USERS.forEach(userService::create);
            log.info("Sample users created: {}", userService.getAll());
        }
        if(articleService.getCount() == 0) {
            try {
                User defaultAuthor = userService.getByUsername("author");
                SAMPLE_ARTICLES.forEach(art -> art.setAuthor(defaultAuthor));
//                defaultAuthor.getArticles().addAll(SAMPLE_ARTICLES);
                SAMPLE_ARTICLES.forEach(articleService::create);
                log.info("Sample articles created: {}", articleService.getAll());
            } catch (EntityNotFoundException ex){
                log.warn("Error creating sample articles: Dafault author could not be found.");
            }
        }
    }
}
