package org.iproduct.spring.webflux.dao;

import org.iproduct.spring.webflux.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
}
