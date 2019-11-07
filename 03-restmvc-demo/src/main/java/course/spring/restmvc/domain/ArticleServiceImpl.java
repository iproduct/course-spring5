package course.spring.restmvc.domain;

import course.spring.restmvc.dao.ArticlesRepository;
import course.spring.restmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesRepository repo;

    @Override
    public List<Article> findAll() {
        return repo.findAll();
    }

    @Override
    public Article findById(String articleId) {
        return null;
    }

    @Override
    public Article add(Article article) {
        return repo.insert(article);
    }

    @Override
    public Article update(Article article) {
        return null;
    }

    @Override
    public Article remove(String articleId) {
        return null;
    }
}
