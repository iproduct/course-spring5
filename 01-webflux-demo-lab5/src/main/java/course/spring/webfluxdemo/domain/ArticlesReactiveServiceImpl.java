package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.dao.ArticlesReactiveRepository;
import course.spring.webfluxdemo.dao.ArticlesRepository;
import course.spring.webfluxdemo.exception.NonexistingEntityException;
import course.spring.webfluxdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
public class ArticlesReactiveServiceImpl implements ArticlesReactiveService{
    @Autowired
    private ArticlesReactiveRepository repository;

    @Override
    public Flux<Article> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Article> getById(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NonexistingEntityException(
                String.format("Article with ID:%s does not exist.", id)
            )));
    }

    @Override
    public Mono<Article> add(Article article) {
        return repository.insert(article);
    }

    @Override
    public Mono<Article> update(Article article) {
       return repository.existsById(article.getId())
        .flatMap(exists -> exists ?
            repository.save(article):
            Mono.error(new NonexistingEntityException(
                String.format("Article with ID:%s does not exist.", article.getId())
            )));
    }

    @Override
    public Mono<Article> delete(String articleId) {
        return repository.findById(articleId)
            .doOnNext(found -> {
                repository.deleteById(articleId).subscribe();
            }).switchIfEmpty(Mono.error(new NonexistingEntityException(
                String.format("Article with ID:%s does not exist.", articleId)
            )));
    }


}
