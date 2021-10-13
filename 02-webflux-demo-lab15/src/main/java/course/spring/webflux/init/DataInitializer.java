package course.spring.webflux.init;

import course.spring.webflux.dao.ArticleRepository;
import course.spring.webflux.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private ArticleRepository articleRepo;

    public static final List<Article> SAMPLE_ARTICLES = List.of(
            new Article("New Spring 5", "WebFlux is here ..."),
            new Article("DI in Action", "To autowire or not autowire ..."),
            new Article("What is Spring Bean?", "Spring managed components ...")
    );

    @Override
    public void run(String... args) throws Exception {
        articleRepo.count()
                .filter(count -> count == 0)
                .flatMapMany(count -> Flux.fromIterable(SAMPLE_ARTICLES)
                            .flatMap(article -> articleRepo.insert(article)))
                .log()
                .subscribe(
                        System.out::println, // next
                        System.err::println, // error
                        () -> System.out.println("Data initialization finished.") // completion
                );
    }
}
