package org.iproduct.spring.webmvcdemo.domain;

import org.iproduct.spring.webmvcdemo.dao.ArticlesRepository;
import org.iproduct.spring.webmvcdemo.exceptions.InvalidEntityIdException;
import org.iproduct.spring.webmvcdemo.exceptions.NonexistingEntityException;
import org.iproduct.spring.webmvcdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesRepository repo;

    @Override
    public List<Article> getArticles() {
        return repo.findAll();
    }

    @Override
    public Article getArticleById(String articleId) {
        return repo.findById(articleId).orElseThrow( ()->
                new NonexistingEntityException(
                        String.format("Article ID=%s does not exist.", articleId) )
        );
    }

    @Override
    public Article add(Article article) {
        return repo.insert(article);
    }

    @Override
    public Article update(Article article) {
        Article found = repo.findById(article.getId()).orElseThrow( () ->
                new NonexistingEntityException(
                    String.format("Article ID=%s does not exist.", article.getId()))
        );
        article.setModified(LocalDateTime.now());
        return repo.save(article);
    }

    @Override
    public Article delete(String articleId) {
        Article toBeDeleted = repo.findById(articleId).orElseThrow( ()->
                new NonexistingEntityException(
                        String.format("Article ID=%s does not exist.", articleId) )
        );
        repo.deleteById(articleId);
        return toBeDeleted;
    }
}
