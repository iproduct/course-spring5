package course.spring.dao.impl;

import course.spring.dao.Identifiable;
import course.spring.dao.KeyGenerator;
import course.spring.dao.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<V extends Identifiable<K>, K> implements Repository<V, K> {
    private Map<K, V> entities = new ConcurrentHashMap<>();
    private KeyGenerator<K> generator;

    public InMemoryRepository(KeyGenerator<K> generator) {
        this.generator = generator;
    }

    @Override
    public List<V> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public Optional<V> findById(K id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public V create(V entity) {
        entity.setId(generator.getNextId());
        return entities.put(entity.getId(), entity);
    }

    @Override
    public Optional<V> update(V entity) {
        var old = entities.get(entity.getId());
        if (old == null) {
            Optional.empty();
        }
        entities.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<V> deleteById(K id) {
        var old = entities.get(id);
        if (old == null) {
            Optional.empty();
        }
        entities.remove(id);
        return Optional.of(old);
    }

    @Override
    public int count() {
        return entities.size();
    }
}
