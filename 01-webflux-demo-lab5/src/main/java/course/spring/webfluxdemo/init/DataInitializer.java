package course.spring.webfluxdemo.init;

import course.spring.webfluxdemo.dao.ArticlesReactiveRepository;
import course.spring.webfluxdemo.domain.ArticlesReactiveService;
import course.spring.webfluxdemo.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private ArticlesReactiveRepository articlesRepo;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting initialization ...");
        articlesRepo.count()
            .flatMapMany(articlesCout ->
                (articlesCout > 0 ?
                    Flux.<Article>empty():
                    Flux.just("Article one", "Article two", "Article three")
                        .flatMap(title -> articlesRepo.insert(
                            Article.builder()
                                .title(title).content("Content of " + title)
                                .build()
                        ))
                )
            ).log()
            .subscribe(
            null,
                error -> log.error("Error initializing MongoDB", error),
                () -> log.info("Done initialization.")
            );
    }
}
