package ws.spring.dao;

public interface IdGenerator<K> {
    K getNextId();
}
