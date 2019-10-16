package org.iproduct.webflux.dao;

import org.iproduct.webflux.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface ReactiveArticlesRepository extends ReactiveMongoRepository<Article, String> {
}
