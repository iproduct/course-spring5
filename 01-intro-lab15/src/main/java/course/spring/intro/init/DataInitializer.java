package course.spring.intro.init;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    public static final List<Article> SAMPLE_ARTICLES = List.of(
         new Article("New in Spring 5", "WebFlux is here ..."),
         new Article("DI in Spring", "To @Autowitre or not to autowire ..."),
         new Article("AOP for Everybody", "Spring AOP is good middle ground ...")
    );
    @Autowired
    private ArticleRepository articleRepository;
    @Override
    public void run(String... args) throws Exception {
        SAMPLE_ARTICLES.forEach(articleRepository::create);
    }
}
