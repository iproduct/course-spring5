package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.dao.ArticleRepository;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Primary
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository repo;

    @Override
    public List<Article> getArticles() {
        return repo.findAll();
    }

    @Override
    public List<Article> getArticlesByAuthorId(String userId) {
        return repo.findByAuthorId(userId);
    }

    @Override
    public Article createArticle(Article article) {
        article.setCreated(LocalDateTime.now());
        article.setUpdated(LocalDateTime.now());
        return repo.insert(article);
    }

    @Override
    @PreAuthorize("(#authorId == authentication.principal.id and #authorId == #article.authorId )" +
            " or hasAuthority('ALL_ARTICLE_UPDATE')")
    public Article updateArticle(String authorId, Article article) {
        article.setUpdated(LocalDateTime.now());
        return repo.save(article);
    }

    @Override
    public Article getArticleById(String id) {
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Article with ID=%s not found.", id)));
    }

    @Override
    @RolesAllowed("ROLE_USER")
//    @Secured("ROLE_ADMIN")
    public Article deleteArticle(String id) {
        Article old = repo.findById(id).orElseThrow( () ->
                new EntityNotFoundException(String.format("Article with ID=%s not found.", id)));
        repo.deleteById(id);
        return old;
    }
}
