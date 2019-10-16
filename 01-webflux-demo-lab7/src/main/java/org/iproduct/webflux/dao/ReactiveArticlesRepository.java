package org.iproduct.webflux.dao;

import org.iproduct.webflux.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


public interface ReactiveArticlesRepository extends ReactiveMongoRepository<Article, String> {
}
