package course.spring.dao.impl;

import course.spring.dao.IdGenFactory;
import course.spring.dao.IdGenerator;

public class LongIdGenFactoryImpl implements IdGenFactory<Long> {
    @Override
    public IdGenerator<Long> createIdGenerator() {
        return new LongIdGenerator();
    }
}
