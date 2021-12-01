package course.spring.webmvc.init;

import course.spring.webmvc.domain.ArticleService;
import course.spring.webmvc.domain.UserService;
import course.spring.webmvc.entity.Article;
import course.spring.webmvc.entity.User;
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
            new User("Default", "Admin", "admin", "Admin123&")
    );

    private final List<Article> DEFAULT_ARTICLES = List.of(
            new Article("New in Spring", "Non-blocking reactive WebFlux web services ..."),
            new Article("Spring Data Mongo DB - What's New?", "References between Mongo DB collections supported by Spring Ddata ..."),
            new Article("Spring Security - is It Easy?", "Even easier with Kotlin DSL ...")
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
                                .map(article -> {article.setAuthorId(author.getId()); return article;})
                                .map(articleService::create).collect(Collectors.toList()));

            }
        }
    }
}
