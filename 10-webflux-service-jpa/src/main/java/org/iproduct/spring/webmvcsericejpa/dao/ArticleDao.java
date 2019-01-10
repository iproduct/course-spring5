package org.iproduct.spring.webmvcsericejpa.dao;

import org.iproduct.spring.webmvcsericejpa.model.Article;
import org.springframework.data.repository.CrudRepository;


public interface ArticleDao extends CrudRepository<Article, Long> {
}
