package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.dao.ReactiveArticleRepository;
import course.spring.webfluxdemo.exception.EntityExistsException;
import course.spring.webfluxdemo.exception.NonexisitngEntityException;
import course.spring.webfluxdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;

public class ReactiveArticleServiceImpl implements  ReactiveArticleService{
    @Autowired
    private ReactiveArticleRepository repo;

    @Override
    public Flux<Article> getAll() {
        return repo.findAll();
    }

    @Override
    public Mono<Article> getById(String articleId) {
        return repo.findById(articleId);
    }

    @Override
    public Mono<Article> create(Article article) {
        return repo.existsById(article.getId())
            .flatMap(exists -> exists ? Mono.empty(): repo.insert(article))
            .switchIfEmpty(Mono.error(
                new EntityExistsException(
                    String.format("Article with ID:%s already exists.", article.getId()))
            ));
    }

    @Override
    public Mono<Article> update(Article article) {
        return repo.findById(article.getId())
            .zipWith(Mono.just(article))
            .flatMap(tuple -> {
                Article old = tuple.getT1();
                Article newArt = tuple.getT2();
                old.setTitle(newArt.getTitle());
                old.setContent(newArt.getContent());
                old.setUpdated(LocalDateTime.now());
                return repo.save(old);
            }).switchIfEmpty(Mono.error(
                new NonexisitngEntityException(
                    String.format("Article with ID:%s does not exist.", article.getId()))
            ));
    }

    @Override
    public Mono<Article> deleteById(String articleId) {
        return repo.findById(articleId)
            .flatMap(art -> repo.deleteById(articleId).thenReturn(art))
            .switchIfEmpty(Mono.error(
                new NonexisitngEntityException(
                    String.format("Article with ID:%s does not exist.", articleId))
            ));
    }
}
