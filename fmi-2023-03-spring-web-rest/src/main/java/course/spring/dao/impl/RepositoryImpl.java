package course.spring.dao.impl;

import course.spring.dao.IdGenerator;
import course.spring.dao.MyRepository;
import course.spring.model.Identifiable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RepositoryImpl<V extends Identifiable<K>, K> implements MyRepository<V, K> {
    private Map<K, V> entities = new ConcurrentHashMap<>();
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
    public Optional<V> update(V entity) {
        return Optional.empty();
    }

    @Override
    public Optional<V> deleteById(K id) {
        return Optional.empty();
    }

    @Override
    public int count() {
        return entities.size();
    }
}
