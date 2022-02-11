package ws.spring.dao;

import ws.spring.model.Article;

import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<T extends Identifiable<K>, K> {
    Collection<T> findAll();
    Optional<T> findById(K id);
    T create(T entity);
    int count();
}
