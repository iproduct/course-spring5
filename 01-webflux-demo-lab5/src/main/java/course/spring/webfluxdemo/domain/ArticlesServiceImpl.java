package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.dao.ArticlesRepository;
import course.spring.webfluxdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticlesServiceImpl implements ArticlesService{
    @Autowired
    private ArticlesRepository repository;

    @Override
    public List<Article> getAll() {
        return repository.findAll();
    }

    @Override
    public Article add(Article article) {
        return repository.create(article);
    }


}
