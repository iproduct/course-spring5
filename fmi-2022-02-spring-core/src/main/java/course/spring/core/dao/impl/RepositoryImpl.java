package course.spring.core.dao.impl;

import course.spring.core.dao.Repository;

import java.util.List;
import java.util.Optional;

public class RepositoryImpl<V, K> implements Repository<V, K> {

    @Override
    public List<V> findAll() {
        return null;
    }

    @Override
    public Optional<V> findById(K id) {
        return Optional.empty();
    }

    @Override
    public V create(V entity) {
        return null;
    }

    @Override
    public V update(V entity) {
        return null;
    }

    @Override
    public Optional<V> deleteById(K id) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return 0;
    }
}
