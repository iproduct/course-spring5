package course.spring.webfluxdemo.init;

import course.spring.webfluxdemo.dao.ReactiveArticleRepository;
import course.spring.webfluxdemo.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private ReactiveArticleRepository repo;

    @Override
    public void run(String... args) throws Exception {
        repo.count()
            .flatMapMany(count -> count > 0 ? Flux.empty() :
                Flux.just(
                    new Article("Spring DI",
                    "DI is needed for component-based engineering ..."),
                    new Article("WebFlux for Performant IO",
                    "WebFlux is the new asyncronous web API in Spring 5 ..."),
                    new Article("Reactor High Performance",
                    "Reactor project allows to achieve tens of millions ops per sec ...")
                ).flatMap(repo::insert)
            ).log()
            .subscribe(null, null,
                () -> log.info("Data initialization completed successfully."));
    }
}
