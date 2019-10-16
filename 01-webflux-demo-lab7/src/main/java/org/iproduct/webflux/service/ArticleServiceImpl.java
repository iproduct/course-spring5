package org.iproduct.webflux.service;

import org.iproduct.webflux.dao.ReactiveArticlesRepository;
import org.iproduct.webflux.exception.NonexisitngEntityException;
import org.iproduct.webflux.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ReactiveArticlesRepository repo;

    @Override
    public Flux<Article> findAll() {
        return repo.findAll();
    }

    @Override
    public Mono<Article> findById(String id) {
        return repo.findById(id)
            .switchIfEmpty(Mono.error(new NonexisitngEntityException(
                    String.format("Article with ID:%s does not exist.", id))));
    }

    @Override
    public Mono<Article> create(Article article) {
        return repo.insert(article);
    }

    @Override
    public Mono<Article> update(Article article) {
        return repo.save(article);
    }

    @Override
    public Mono<Article> delete(String articleId) {
        return repo.findById(articleId)
        .flatMap(art -> repo.deleteById(articleId).thenReturn(art));
    }

    @Override
    public Mono<Long> getCount() {
        return repo.count();
    }
}
