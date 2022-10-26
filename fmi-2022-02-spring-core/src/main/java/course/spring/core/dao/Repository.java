package course.spring.core.dao;

import course.spring.core.model.Identifiable;

import java.util.List;
import java.util.Optional;

public interface Repository<V extends Identifiable<K>, K> {
    List<V> findAll();
    Optional<V> findById(K id);
    V create(V entity);
    V update(V entity);
    Optional<V> deleteById(K id);
    long count();
}
