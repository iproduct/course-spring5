package course.spring.init;


import course.spring.model.Article;
import course.spring.model.User;
import course.spring.service.ArticleService;
import course.spring.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

import static course.spring.model.Role.*;

@Component
public class DataInitializer implements CommandLineRunner {


    public static final User DEFAULT_USER = new User("Default", "Admin", "admin", "admin123", Set.of(READER, AUTHOR, ADMIN));

    private ArticleService articleService;
    private UserService userService;

    public DataInitializer(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.getUserCount() == 0) {
            userService.createUser(DEFAULT_USER);
        }
        if (articleService.getArticleCount() == 0) {
            articleService.createArticle(new Article("Spring Intro", "Spring is developer friendly web service platform",
                    "https://www.publicdomainpictures.net/pictures/10000/velka/1-1235819207MtlN.jpg",
                    DEFAULT_USER, Set.of("spring", "intro")));
            articleService.createArticle(new Article("Spring Data JPA", "Spring Data JPA allows easy RDBMS persis1tence.",
                    "https://www.publicdomainpictures.net/pictures/270000/velka/seodata-big-data-analytics-site.jpg",
                    DEFAULT_USER, Set.of("spring data", "jpa")));
        }
    }
}
