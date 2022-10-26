package course.spring.core.dao.impl;

import course.spring.core.dao.IdGenerator;
import course.spring.core.dao.Repository;
import course.spring.core.model.Identifiable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryImpl<V extends Identifiable<K>, K> implements Repository<V, K> {
    private Map<K,V> entities = new ConcurrentHashMap<>();
    private IdGenerator<K> idGen;

    public RepositoryImpl(IdGenerator<K> idGen) {
        this.idGen = idGen;
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
        entity.setId(idGen.getNextId());
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public V update(V entity) {
        entities.put(entity.getId(), entity);
        return entity;
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
