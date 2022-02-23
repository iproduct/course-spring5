package ws.spring.dao.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import ws.spring.dao.ArticleRepository;
import ws.spring.model.Article;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
//@Scope("singleton")
public class ArticleRepositoryMemoryImpl implements ArticleRepository {
    private Map<Long, Article> entities = new ConcurrentHashMap<>();
    private static AtomicLong nextId = new AtomicLong();

    @Override
    public Collection<Article> findAll() {
        return entities.values();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Article create(Article article) {
        article.setId(nextId.incrementAndGet());
        entities.put(article.getId(), article);
        return article;
    }

    @Override
    public int count() {
        return entities.size();
    }
}
