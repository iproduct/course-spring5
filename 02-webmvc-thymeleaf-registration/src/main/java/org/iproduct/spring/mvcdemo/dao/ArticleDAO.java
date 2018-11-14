package org.iproduct.spring.mvcdemo.dao;

import org.iproduct.spring.mvcdemo.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleDAO extends MongoRepository<Article, String> {
}
