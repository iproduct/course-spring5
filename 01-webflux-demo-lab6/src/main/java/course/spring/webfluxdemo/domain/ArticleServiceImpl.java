package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.dao.ArticleRepository;
import course.spring.webfluxdemo.exception.NonexisitngEntityException;
import course.spring.webfluxdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleRepository repo;

    @Override
    public Collection<Article> getAll() {
        return repo.findAll();
    }

    @Override
    public Article getById(String articleId) throws NonexisitngEntityException {
        return repo.findById(articleId).orElseThrow(() ->
            new NonexisitngEntityException(
                String.format("Article with ID:%s does not exist.",articleId)
            ));
    }

    @Override
    public Article create(Article article) {
        return repo.insert(article);
    }

    @Override
    public Article update(Article article) {
        return repo.save(article);
    }

    @Override
    public Article deleteById(String articleId) throws NonexisitngEntityException{
        Article deleted = repo.deleteById(articleId);
        if(deleted == null) {
            throw new NonexisitngEntityException(
                String.format("Article with ID:%s does not exist.", articleId)
            );
        }
        return deleted;
    }
}
