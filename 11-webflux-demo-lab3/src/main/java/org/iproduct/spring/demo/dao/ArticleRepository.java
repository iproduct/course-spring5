package org.iproduct.spring.demo.dao;

import org.iproduct.spring.demo.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
}