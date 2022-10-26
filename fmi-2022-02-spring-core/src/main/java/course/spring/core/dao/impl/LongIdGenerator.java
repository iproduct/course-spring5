package course.spring.core.dao.impl;

import course.spring.core.dao.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class LongIdGenerator implements IdGenerator<Long> {
    private AtomicLong sequence;

    public LongIdGenerator() {
        this.sequence =  new AtomicLong();
    }

    public LongIdGenerator(long initialValue) {
        this.sequence = new AtomicLong(initialValue);
    }

    @Override
    public Long getNextId() {
        return sequence.incrementAndGet();
    }
}
