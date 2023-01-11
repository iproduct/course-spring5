package course.spring.blogs.init;

import course.spring.blogs.entity.Article;
import course.spring.blogs.entity.Role;
import course.spring.blogs.entity.User;
import course.spring.blogs.service.ArticleService;
import course.spring.blogs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
                    "Spring Data JPA is easy ...", "https://example.com/images/1",
                    Set.of("spring", "mvc", "boot", "intro"),SAMPLE_USERS.get(1)),
            new Article("Spring Data JPA and Hibernate",
                    "Hibernate provides powerful ORM implementation ...", "https://example.com/images/2",
                    Set.of("hibernate", "performance"), SAMPLE_USERS.get(1)),
            new Article( "Spring Data Integrations",
                    "Spring Data provides reactive db integrations for a number of databases ...",
                    "https://example.com/images/3",
                    Set.of("spring", "boot", "intro"), SAMPLE_USERS.get(1))
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
        if(userService.getUsersCount() == 0) {
            SAMPLE_USERS.forEach(userService::create);
            log.info("Sample users created: {}", userService.getAllUsers());
        }
        if(articleService.getArticlesCount() == 0) {
            try {
                var defaultAuthor = userService.getUserByUsername("admin");
                SAMPLE_ARTICLES.forEach(art -> art.setAuthor(defaultAuthor));
                defaultAuthor.getArticles().addAll(SAMPLE_ARTICLES);
                articleService.createBatch(SAMPLE_ARTICLES);
                log.info("Sample articles created: {}", articleService.getAllArticles());
            } catch (EntityNotFoundException ex){
                log.warn("Error creating sample articles: Dafault author could not be found.");
            } catch (Exception ex) {
                log.error("Error creating sample articles:", ex);
            }
        }
    }
}
