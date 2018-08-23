package org.iproduct.spring.webmvc.dao;

import org.iproduct.spring.webmvc.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
}