package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.dao.ArticleRepository;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class RepositoryArticleProvider implements ArticleProvider {

    @Autowired
    private ArticleRepository repo;

    @Override
    public List<Article> getArticles() {
        return repo.findAll();
    }

    @Override
    public Article getArticleById(String id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Entity with ID=%s not found.", id)));
    }

    @Override
    public Article addArticle(Article article) {
        return repo.save(article);
    }
}
