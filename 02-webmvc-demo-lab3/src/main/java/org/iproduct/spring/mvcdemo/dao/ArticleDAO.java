package org.iproduct.spring.mvcdemo.dao;

import org.iproduct.spring.mvcdemo.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleDAO extends MongoRepository<Article, String> {
}
