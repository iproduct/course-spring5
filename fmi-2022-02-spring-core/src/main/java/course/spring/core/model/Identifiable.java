package course.spring.core.model;

public interface Identifiable<K> {
    K getId();
    void setId(K id);
}
