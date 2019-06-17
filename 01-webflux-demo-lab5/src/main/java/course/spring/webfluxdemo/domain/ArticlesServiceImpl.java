package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.dao.ArticlesRepository;
import course.spring.webfluxdemo.exception.NonexistingEntityException;
import course.spring.webfluxdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ArticlesServiceImpl implements ArticlesService{
    @Autowired
    private ArticlesRepository repository;

    @Override
    public Collection<Article> getAll() {
        return repository.findAll();
    }

    @Override
    public Article add(Article article) {
        return repository.create(article);
    }

    @Override
    public Article update(Article article) throws NonexistingEntityException {
        return repository.update(article);
    }

    @Override
    public Article delete(String articleId) throws NonexistingEntityException {
        return repository.delete(articleId);
    }


}
