package course.spring.intro.init;

import course.spring.intro.dao.ArticleRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import course.spring.intro.entity.Article;

import java.util.List;

@Component
public class DbInitializer implements CommandLineRunner {
    public static final List<Article> DEFAULT_ARTICLES = List.of(
            new Article("New in Spring 6", "Spring AI is a new Spring 6 feature ...", "Trayan Iliev"),
            new Article("DI for Java", "Dependency Injection is a major design pattern in compnent-oriented frameworks ...", "Trayan Iliev"),
            new Article("Running Spring AI Locally", "The post demonstrates how to run Spring AI using Ollama ...", "Trayan Iliev")
        );
    @Autowired
    ArticleRepository articleRepository;
    @Override
    public void run(String... args) throws Exception {
        if (articleRepository.count() == 0) {
            articleRepository.saveAll(DEFAULT_ARTICLES);
        }
    }
}
