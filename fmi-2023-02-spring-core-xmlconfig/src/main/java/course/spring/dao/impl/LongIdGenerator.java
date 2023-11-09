package course.spring.dao.impl;

import course.spring.dao.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class LongIdGenerator implements IdGenerator<Long> {
    private AtomicLong nextId = new AtomicLong();
    @Override
    public Long getNextId() {
        return nextId.incrementAndGet();
    }
}
