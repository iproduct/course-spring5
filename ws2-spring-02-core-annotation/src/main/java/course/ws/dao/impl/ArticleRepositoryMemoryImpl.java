package course.ws.dao.impl;

import course.ws.dao.ArticleRepository;
import course.ws.model.Article;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository("articleRepo")
public class ArticleRepositoryMemoryImpl implements ArticleRepository {
    private Map<Long, Article> entities = new ConcurrentHashMap<>();
    private AtomicLong idGenerator = new AtomicLong();

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
        article.setId(idGenerator.incrementAndGet());
        entities.put(article.getId(), article);
        return article;
    }

    @Override
    public Optional<Article> update(Article article) {
        Article old = entities.get(article.getId());
        if (old == null) {
            return Optional.empty();
        }
        return Optional.of(entities.put(article.getId(), article));
    }

    @Override
    public Optional<Article> deleteById(Long id) {
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public long count() {
        return entities.size();
    }
}
