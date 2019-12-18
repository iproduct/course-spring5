package course.spring.webmvc.init;

import course.spring.webmvc.domain.ArticlesService;
import course.spring.webmvc.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private ArticlesService articlesService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Initializing application...");
        if (articlesService.count() == 0) {
            Article article = new Article("Article 1", "Article 1 content ...");
            log.info("Creating new articles: {}", article);
            articlesService.add(article);
        }
    }

}
