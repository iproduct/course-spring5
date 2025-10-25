package course.spring.coredemo.util.impl;

import course.spring.coredemo.util.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

//@Component
public class LongIdGenerator implements IdGenerator<Long> {
    private AtomicLong id = new AtomicLong();
    @Override
    public Long getNextId() {
        return id.incrementAndGet();
    }
}
