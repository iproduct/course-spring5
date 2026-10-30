package course.spring.dao.impl;

import course.spring.dao.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class IdGeneratorLong implements IdGenerator<Long> {
    private AtomicLong nextId;

    public IdGeneratorLong() {
        AtomicLong nextId = new AtomicLong(0);
    }

    public IdGeneratorLong(long initialId) {
        nextId = new AtomicLong(initialId);
    }

    @Override
    public Long getNextId() {
        return nextId.incrementAndGet();
    }
}
