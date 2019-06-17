package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    List<Article> findByAuthorId(String authorUserId);
}
