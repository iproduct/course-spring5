package course.ws.blogs.init;

import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import course.ws.blogs.service.ArticleService;
import course.ws.blogs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.ws.blogs.entity.Role.*;

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
    public static final List<User> DEFAULT_USERS = List.of(
            new User("Default", "Admin", "admin@mydomain.com", "admin", ADMIN),
            new User("Default", "Author", "author@mydomain.com", "author", AUTHOR),
            new User("Default", "Reader", "reader@mydomain.com", "reader", READER)
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
            log.info("Default users created: {}",
                    DEFAULT_USERS.stream().map(userService::create).collect(Collectors.toList()));
        }
        if(articleService.count() == 0) {
            log.info("Sample articles created: {}",
                    SAMPLE_ARTICLES.stream().map(articleService::create).collect(Collectors.toList()));
        }
    }
}
