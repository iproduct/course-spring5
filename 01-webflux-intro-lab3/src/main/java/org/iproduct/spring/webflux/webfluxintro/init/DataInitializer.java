package org.iproduct.spring.webflux.webfluxintro.init;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.webflux.webfluxintro.dao.ArticleRepository;
import org.iproduct.spring.webflux.webfluxintro.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private ArticleRepository repo;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting initialization ...");
        repo.deleteAll()
                .thenMany(Flux.just("Aricle one", "Aricle two", "Aricle three"))
                .flatMap(title -> repo.save(Article.builder()
                        .title(title)
                        .content(title + " content ...").build()
                )).log()
                .subscribe(null,
                        null,
                        () -> log.info("done initialization."));
    }
}
