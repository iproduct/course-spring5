package course.spring.dao;

public interface IdGenerator<K> {
    K getNextId();
}
