package org.iproduct.spring.restmvc.init;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.service.ArticleService;
import org.iproduct.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

        // Create default users
        long usersCount = userService.getUsersCount();
        log.info("Users count: {}", usersCount);

        List<User> users = new ArrayList<>();

        if (usersCount == 0) {
            List<User> defaultUsers = Arrays.asList(new User[]{
                    new User("admin", "admin", "DEFAULT", "ADMIN", "ROLE_ADMIN"),
                    new User("ivan", "ivan", "Ivan", "Petrov", "ROLE_USER")
            });

            long countBefore = userService.getUsersCount();
            try {
                List<User> created = userService.createUsersBatch(defaultUsers);
                log.info(">>> User batch created: {}", created);
            } catch (ConstraintViolationException ex) {
                log.error(">>> Constraint violation inserting articles: {} - {}", users, ex.getMessage());
            }
            long countAfter = userService.getUsersCount();
            log.info(">>> Total count of users created: {}", countAfter - countBefore);

            log.info("Querying for user records:");
            users = userService.getUsers();
            users.forEach(user -> log.info(user.toString()));
        }

        // Articles
        usersCount = userService.getUsersCount();
        if (usersCount > 0) {
            List<Article> mockArticles = Arrays.asList(
                    new Article("Welcome to Spring 5", "Spring 5 is great beacuse ...", users.get(0)),
                    new Article("Dependency Injection", "Should I use DI or lookup ...", users.get(0)),
                    new Article("Spring News", "There are several ways to configure Spring beans.", users.get(0))
            );
            long countBefore = articleService.getArticlesCount();
            try {
                List<Article> created = articleService.createArticlesBatch(mockArticles);
                log.info(">>> Articles batch created: {}", created);
            } catch (Exception ex) {
                Throwable cause = ex;
                while (cause.getCause() != null) {
                    cause = cause.getCause();
                }
                log.error(">>> Constraint violation inserting articles: {} - {}", mockArticles, cause.getMessage());
            }
            long countAfter = articleService.getArticlesCount();
            log.info(">>> Total count of articles created: {}", countAfter - countBefore);
        }

    }
}
