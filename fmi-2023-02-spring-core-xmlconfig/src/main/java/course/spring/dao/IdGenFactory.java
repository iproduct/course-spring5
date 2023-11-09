package course.spring.dao;

import course.spring.dao.IdGenerator;

public interface IdGenFactory<K> {
    IdGenerator<K> createIdGenerator();
}
