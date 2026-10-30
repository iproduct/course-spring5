package course.spring.dao.impl;

import course.spring.dao.ArticlesRepository;
import course.spring.dao.IdGenerator;
import course.spring.model.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleRepositoryInMemory implements ArticlesRepository {
    private Map<Long, Article> articles = new HashMap<>();
    private IdGenerator<Long> idGen;

    public ArticleRepositoryInMemory(IdGenerator<Long> idGen) {
        this.idGen = idGen;
    }

    public List<Article> findAll() {
        return articles.values().stream().collect(Collectors.toList());
    }

    @Override
    public Article create(Article article) {
        article.setId(idGen.getNextId());
        articles.put(article.getId(), article);
        return article;
    }


}
