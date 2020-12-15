package org.iproduct.spring.restmvc.init;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.UserMapper;
import org.iproduct.spring.restmvc.dao.UserRepository;
import org.iproduct.spring.restmvc.dao.UserRepositoryImpl;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.service.ArticleService;
import org.iproduct.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private static final List<Article> mockArticles = Arrays.asList(
            new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
            new Article("Dependency Injection", "Should I use DI or lookup ..."),
            new Article("Spring", "There are several ways to configure Spring beans.")
    );

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        long countBefore = articleService.getArticlesCount();
        if(countBefore == 0) {
            try {
                List<Article> created = articleService.createArticlesBatch(mockArticles);
                log.info(">>> Articles batch created: {}", created);
            } catch (ConstraintViolationException ex) {
                log.error(">>> Constraint violation inserting articles: {} - {}", mockArticles, ex.getMessage());
            }
            long countAfter = articleService.getArticlesCount();
            log.info(">>> Total count of articles created: {}", countAfter - countBefore);
        }


        // Users initialization
        log.info("Start data initialization  ...");
//        jdbcTemplate.getJdbcOperations().execute("DROP TABLE IF EXISTS users");
        jdbcTemplate.getJdbcOperations().execute("CREATE TABLE IF NOT EXISTS users(" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(40), " +
                "password VARCHAR(80), " +
                "fname VARCHAR(40), " +
                "lname VARCHAR(40), " +
                "roles VARCHAR(80), " +
                "active BOOLEAN, " +
                "created TIMESTAMP, " +
                "updated TIMESTAMP" +
                ")");

        int usersCount = jdbcTemplate.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        log.info("Users count: {}", usersCount);

        if (usersCount == 0) {
            List<User> users = Arrays.asList(new User[] {
                new User("admin", "admin", "DEFAULT", "ADMIN", "ROLE_ADMIN"),
                new User("ivan", "ivan", "Ivan", "Petrov", "ROLE_USER")
            });

            users.stream().forEach(user -> userService.createUser(user));

//            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(users);
//            jdbcTemplate.batchUpdate(UserRepositoryImpl.INSERT_SQL, batch);
        }

        log.info("Querying for user records:");
        this.jdbcTemplate
                .query("select * from users", new UserMapper())
                .forEach(user -> log.info(user.toString()));
    }



}
