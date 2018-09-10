package org.iproduct.spring.restmvc.init;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.service.ArticleService;
import org.iproduct.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        List<Article> mockArticles = Arrays.asList(
                new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
                new Article("Dependency Injection", "Should I use DI or lookup ..."),
                new Article("Spring", "There are several ways to configure Spring beans.")
        );
        long countBefore = articleService.getArticlesCount();
        try {
            List<Article> created = articleService.createArticlesBatch(mockArticles);
            log.info(">>> Articles batch created: {}", created);
        } catch (ConstraintViolationException ex) {
            log.error(">>> Constraint violation inserting articles: {} - {}", mockArticles, ex.getMessage());
        }
        long countAfter = articleService.getArticlesCount();
        log.info(">>> Total count of articles created: {}", countAfter - countBefore);


        // Create default users
        long usersCount = userService.getUsersCount();
        log.info("Users count: {}", usersCount);

        if (usersCount == 0) {
            List<User> users = Arrays.asList(new User[]{
                    new User("admin", "admin", "DEFAULT", "ADMIN", "ROLE_ADMIN"),
                    new User("ivan", "ivan", "Ivan", "Petrov", "ROLE_USER")
            });

            countBefore = userService.getUsersCount();
            try {
                List<User> created = userService.createUsersBatch(users);
                log.info(">>> User batch created: {}", created);
            } catch (ConstraintViolationException ex) {
                log.error(">>> Constraint violation inserting articles: {} - {}", users, ex.getMessage());
            }
            countAfter = userService.getUsersCount();
            log.info(">>> Total count of users created: {}", countAfter - countBefore);

            log.info("Querying for user records:");
            userService.getUsers().forEach(user -> log.info(user.toString()));
        }

    }
}
