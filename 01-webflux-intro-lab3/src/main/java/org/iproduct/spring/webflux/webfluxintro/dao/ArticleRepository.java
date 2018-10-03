package org.iproduct.spring.webflux.webfluxintro.dao;

import org.iproduct.spring.webflux.webfluxintro.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {}
