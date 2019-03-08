package patchdemo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import patchdemo.model.Article;

@Repository
public interface ArticlesRepository extends MongoRepository<Article, String> {
}
