package org.iproduct.spring.webflux.webfluxintro.service;

import org.iproduct.spring.webflux.webfluxintro.dao.ArticleRepository;
import org.iproduct.spring.webflux.webfluxintro.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
//    private static final List<Article> articles = Arrays.asList(new Article[]{
//            new Article("11111111111111111111", "New in Spring", "WebFlux is is here ..."),
//            new Article("22222222222222222222", "Depenedency Injection", "DI is easy ..."),
//            new Article("33333333333333333333", "What is REST?", "REST requires HATEOAS ...")
//    });

    @Autowired
    private ArticleRepository repo;

    @Override
    public Flux<Article> getArticles() {
        return repo.findAll();
    }
}
