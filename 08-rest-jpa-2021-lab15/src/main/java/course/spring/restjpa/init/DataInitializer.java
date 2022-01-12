package course.spring.restjpa.init;

import course.spring.restjpa.entity.Article;
import course.spring.restjpa.entity.User;
import course.spring.restjpa.service.ArticleService;
import course.spring.restjpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    private final List<User> DEFAULT_USERS = List.of(
            new User("admin", "Admin123&", "Default", "Admin", "AUTHOR, ADMIN"),
            new User("author", "Author123&", "Default", "Author")
    );

    private final List<Article> DEFAULT_ARTICLES = List.of(
            new Article("New in Spring", "Non-blocking reactive WebFlux web services ...", List.of("spring", "java", "webflux")),
            new Article("Spring Data Mongo DB - What's New?",
                    "References between Mongo DB collections supported by Spring Ddata ...", List.of("spring", "data", "mongo")),
            new Article("Spring Security - is It Easy?", "Even easier with Kotlin DSL ...", List.of("spring", "kotlin", "security"))
    );

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userService.count() == 0) {
            log.info("Successfully created users: {}",
                    DEFAULT_USERS.stream().map(userService::create).collect(Collectors.toList()));
        }
        if(articleService.count() == 0) {
            List<User> users = userService.findAll();
            if (users.size() > 0) {
                User author = users.get(0);
                log.info("Successfully created articles: {}",
                        DEFAULT_ARTICLES.stream()
                                .map(article -> {article.setAuthor(author); return article;})
                                .map(articleService::create).collect(Collectors.toList()));

            }
        }
    }
}
