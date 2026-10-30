package course.spring.dao;

public interface IdGenerator<T> {
    T getNextId();
}
