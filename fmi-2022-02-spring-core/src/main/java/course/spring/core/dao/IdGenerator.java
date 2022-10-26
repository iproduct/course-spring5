package course.spring.core.dao;

public interface IdGenerator<K> {
    K getNextId();
}
