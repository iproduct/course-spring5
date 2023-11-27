package course.spring.dao.impl;

import course.spring.dao.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class LongIdGenerator implements IdGenerator<Long> {
    private AtomicLong nextId = new AtomicLong();
    @Override
    public Long getNextId() {
        return nextId.incrementAndGet();
    }
}
