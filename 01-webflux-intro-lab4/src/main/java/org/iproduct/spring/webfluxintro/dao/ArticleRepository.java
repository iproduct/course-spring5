package org.iproduct.spring.webfluxintro.dao;

import org.iproduct.spring.webfluxintro.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
}
