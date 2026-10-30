package course.spring.dao.impl;

import course.spring.dao.ArticlesRepository;
import course.spring.dao.IdGenerator;
import course.spring.model.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleRepositoryInMemory implements ArticlesRepository {
    private Map<Long, Article> articles = new HashMap<>();
    private

    public ArticleRepositoryInMemory(IdGenerator<Long> idGen) {
    }
    public List<Article> findAll() {
        return List.of();
    }

    @Override
    public Article create(Article article) {
        return null;
    }


}
