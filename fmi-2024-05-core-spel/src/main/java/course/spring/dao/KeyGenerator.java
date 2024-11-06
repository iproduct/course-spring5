package course.spring.dao;

public interface KeyGenerator<K> {
    K getNextId();
}
