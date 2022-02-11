package ws.spring.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ws.spring.dao.GenericRepository;
import ws.spring.dao.IdGenerator;
import ws.spring.dao.Identifiable;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryMemoryImpl<T extends Identifiable<K>, K> implements GenericRepository<T, K> {
    private Map<K, T> entities = new ConcurrentHashMap<>();
    private IdGenerator<K> generator;

    @Autowired
    public RepositoryMemoryImpl(IdGenerator<K> generator) {
        this.generator = generator;
    }

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public Optional<T> findById(K id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public T create(T entity) {
        entity.setId(generator.getNextId());
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public int count() {
        return entities.size();
    }
}
