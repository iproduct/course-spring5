package course.spring.intro.init;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.dao.UserRepository;
import course.spring.intro.entity.Role;
import course.spring.intro.entity.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import course.spring.intro.entity.Article;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public class DbInitializer implements CommandLineRunner {
    private static final List<User> DEFAULT_USERS = List.of(
            new User("Default", "Admin", "admin@mycomp.com", "admin", "admin123", Set.of(Role.ADMIN)),
            new User("Default", "Author", "admin@mycomp.com", "author", "admin123", Set.of(Role.AUTHOR)),
            new User("Default", "Reader", "admin@mycomp.com", "reader", "reader123", Set.of(Role.READER))
    );
    public static final List<Article> DEFAULT_ARTICLES = List.of(
            new Article("New in Spring 6", "Spring AI is a new Spring 6 feature ...", DEFAULT_USERS.get(0)),
            new Article("DI for Java", "Dependency Injection is a major design pattern in compnent-oriented frameworks ...", DEFAULT_USERS.get(1)),
            new Article("Running Spring AI Locally", "The post demonstrates how to run Spring AI using Ollama ...", DEFAULT_USERS.get(1))
        );
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.saveAll(DEFAULT_USERS);
        }
        if (articleRepository.count() == 0) {
            articleRepository.saveAll(DEFAULT_ARTICLES);
        }
    }
}
