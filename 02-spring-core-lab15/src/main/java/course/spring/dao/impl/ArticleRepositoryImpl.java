package course.spring.dao.impl;

import course.spring.dao.ArticleRepository;
import course.spring.model.Article;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    private Map<Long, Article> articles = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    @Override
    public Collection<Article> findAll() {
        return articles.values();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public Article create(Article article) {
        article.setId(nextId.incrementAndGet());
        return articles.put(article.getId(), article);
    }
}
