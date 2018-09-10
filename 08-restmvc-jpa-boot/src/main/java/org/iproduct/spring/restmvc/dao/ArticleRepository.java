package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.Article;
import org.springframework.data.repository.CrudRepository;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAll();
}
