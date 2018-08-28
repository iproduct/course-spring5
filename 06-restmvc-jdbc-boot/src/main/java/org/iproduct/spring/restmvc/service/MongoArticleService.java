package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.dao.ArticleRepository;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@Primary
public class MongoArticleService implements ArticleService {

    @Autowired
    ArticleRepository repo;

    @Override
    public Collection<Article> getArticles() {
        return repo.findAll();
    }

    @Override
    public Article addArticle(Article article) {
        article.setCreated(LocalDateTime.now());
        return repo.insert(article);
    }

    @Override
    public Article updateArticle(Article article) {
        return repo.save(article);
    }

    @Override
    public Article getArticleById(long id) {
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Article with ID=%s not found.", id)));
    }

    @Override
    public Article deleteArticle(long id) {
        Article old = repo.findById(id).orElseThrow( () ->
                new EntityNotFoundException(String.format("Article with ID=%s not found.", id)));
        repo.deleteById(id);
        return old;
    }
}
