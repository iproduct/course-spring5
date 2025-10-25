package course.spring.coredemo.service.impl;

import course.spring.coredemo.model.Article;
import course.spring.coredemo.service.ArticleProvider;
import course.spring.coredemo.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//@Service("inMemoryProvider")
public class ArticleProviderInMemory implements ArticleProvider {
    private Map<Long, Article> articles = new ConcurrentHashMap<>();
    private IdGenerator<Long> idGenerator;

    public static ArticleProviderInMemory create(IdGenerator<Long> idGen) {
        return new ArticleProviderInMemory(idGen);
    }

//    @Autowired
    public ArticleProviderInMemory(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public List<Article> getArticles() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Article createArticle(Article article) {
        article.setId(idGenerator.getNextId());
        articles.put(article.getId(), article);
        return article;
    }
}
