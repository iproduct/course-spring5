package course.spring.core.dao;

import java.util.List;
import java.util.Optional;

public interface Repository<V, K> {
    List<V> findAll();
    Optional<V> findById(K id);
    V create(V entity);
    V update(V entity);
    Optional<V> deleteById(K id);
    long count();
}
