package course.ws.dao.impl;

import course.ws.dao.Repository;
import course.ws.model.Article;
import course.ws.model.Identifiable;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class RepositoryMemoryImpl<K, V extends Identifiable<K>> implements Repository<K, V> {
    private Map<K, V> entities = new ConcurrentHashMap<>();
    private AtomicLong idGenerator = new AtomicLong();

    @Override
    public Collection<V> findAll() {
        return entities.values();
    }

    @Override
    public Optional<V> findById(K id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public V create(V article) {
//        article.setId(idGenerator.incrementAndGet());
        entities.put(article.getId(), article);
        return article;
    }

    @Override
    public Optional<V> update(V article) {
        V old = entities.get(article.getId());
        if (old == null) {
            return Optional.empty();
        }
        return Optional.of(entities.put(article.getId(), article));
    }

    @Override
    public Optional<V> deleteById(K id) {
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public long count() {
        return entities.size();
    }
}
