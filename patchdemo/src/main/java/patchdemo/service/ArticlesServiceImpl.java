package patchdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import patchdemo.dao.ArticlesRepository;
import patchdemo.exception.EntityNotFoundException;
import patchdemo.model.Article;

import java.util.List;

@Service
@Primary
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    ArticlesRepository repo;

    @Override
    public List<Article> getAllArticles() {
        return repo.findAll();
    }

    @Override
    public Article createArticle(Article article) {
        return repo.insert(article);
    }

    @Override
    public Article updateArticle(Article article) {
        return repo.save(article);
    }

    @Override
    public Article getArticleById(String id) {
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Article with ID=%s not found.", id)));
    }

    @Override
    public Article deleteArticle(String id) {
        Article old = repo.findById(id).orElseThrow( () ->
                new EntityNotFoundException(String.format("Article with ID=%s not found.", id)));
        repo.deleteById(id);
        return old;
    }

    @Override
    public void deleteAllArticles() {
        repo.deleteAll();
    }
}
