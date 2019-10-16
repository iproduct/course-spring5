package org.iproduct.webflux.service;

import org.iproduct.webflux.dao.ReactiveArticlesRepository;
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
        return null;
    }

    @Override
    public Mono<Article> create(Article article) {
        return null;
    }

    @Override
    public Mono<Article> update(Article article) {
        return null;
    }

    @Override
    public Mono<Article> delete(String articleId) {
        return null;
    }

    @Override
    public Mono<Integer> getCount() {
        return null;
    }
}
