package course.hibernate.spring.entity;

public interface Property<T> {
    String getName();
    T getValue();
}
