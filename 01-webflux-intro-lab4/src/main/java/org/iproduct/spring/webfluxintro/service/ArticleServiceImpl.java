package org.iproduct.spring.webfluxintro.service;

import org.iproduct.spring.webfluxintro.dao.ArticleMockRepository;
import org.iproduct.spring.webfluxintro.dao.ArticleRepository;
import org.iproduct.spring.webfluxintro.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository repo;

    @Override
    public Flux<Article> getAllArticles() {
        return repo.findAll();
    }

    @Override
    public Mono<Article> create(Article article) {
        return repo.save(article);
    }

    @Override
    public Mono<Article> getArticleById(String id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Article> deleteById(String id) {
        return repo.findById(id).flatMap( article ->
                repo.deleteById(id).log().thenReturn(article));
    }
}
