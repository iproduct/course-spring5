package org.iproduct.spring.webflux.webfluxintro.service;

import org.iproduct.spring.webflux.webfluxintro.model.Article;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ArticleService {
    Flux<Article> getArticles();
}
