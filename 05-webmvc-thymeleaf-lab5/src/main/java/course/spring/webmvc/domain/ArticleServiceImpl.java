package course.spring.webmvc.domain;

import course.spring.webmvc.dao.ArticlesRepository;
import course.spring.webmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<Article> getArticleById(String articleId) {
        return repo.findById(articleId);
    }

    @Override
    public Article add(Article article) {
        return repo.insert(article);
    }

    @Override
    public Article update(Article article) {
        return repo.save(article);
    }

    @Override
    public Optional<Article> delete(String articleId) {
        Optional<Article> toBeDeleted = repo.findById(articleId);
        if(toBeDeleted.isPresent()) {
            repo.deleteById(articleId);
        }
        return toBeDeleted;
    }
}
