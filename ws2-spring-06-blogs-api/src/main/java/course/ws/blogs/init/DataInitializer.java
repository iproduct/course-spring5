package course.ws.blogs.init;

import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.service.ArticleService;
import course.ws.blogs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.ws.blogs.entity.Role.*;

@Component
@Slf4j
@Profile("!test")
public class DataInitializer implements ApplicationRunner {
    public static final List<User> DEFAULT_USERS = List.of(
            new User("Default", "Admin", "admin@mydomain.com", "admin", ADMIN),
            new User("Default", "Author", "author@mydomain.com", "author", AUTHOR),
            new User("Default", "Reader", "reader@mydomain.com", "reader", READER)
    );
    public static final List<Article> SAMPLE_ARTICLES = List.of(
            new Article("Intro to Spring", "Spring MVC is easy ...",
                    DEFAULT_USERS.get(1), Set.of("spring", "mvc", "boot", "intro")),
            new Article("Hibernate Performance", "Hibernate provides powerful ORM implementation ...",
                    DEFAULT_USERS.get(1), Set.of("hibernate", "performance")),
            new Article( "Spring Boot is Easy", "Spring Boot makes bootstrapping new Spring projects easy ...",
                    DEFAULT_USERS.get(1), Set.of("spring", "boot", "intro"))
    );


    private ArticleService articleService;
    private UserService userService;

    @Autowired
    public DataInitializer(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userService.count() == 0) {
            var users = DEFAULT_USERS.stream().map(userService::create).collect(Collectors.toList());
            log.info("Default users created: {}", users);
        }
        if(articleService.count() == 0) {
            try {
                User defaultAuthor = userService.findUserByEmail("author@mydomain.com");
                SAMPLE_ARTICLES.forEach(a -> a.setAuthor(defaultAuthor));
                log.info("Sample articles created: {}",
                        SAMPLE_ARTICLES.stream().map(articleService::create).collect(Collectors.toList()));
            } catch (EntityNotFoundException ex) {
                log.warn("Error creating sample articles: Default author not found.", ex);
            }
        }
    }
}
