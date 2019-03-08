package patchdemo.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import patchdemo.model.Article;
import patchdemo.service.ArticlesService;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    public static final List<Article> SAMPLE_ARTICLES = Arrays.asList(
            new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
            new Article("Dependency Injection", "Should I use DI or lookup ..."),
            new Article("Spring Beans and Wiring", "There are several ways to configure Spring beans.")
    );


    @Autowired
    private ArticlesService articlesService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Starting data initialization  ...");
        articlesService.deleteAllArticles();
        long articlesCreated = SAMPLE_ARTICLES.stream().map(
            article -> articlesService.createArticle(article)
        ).count();
        log.info("Initialization finished: {} articles created", articlesCreated);
    }

}

