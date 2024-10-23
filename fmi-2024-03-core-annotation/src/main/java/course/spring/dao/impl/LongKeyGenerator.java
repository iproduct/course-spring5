package course.spring.dao.impl;

import course.spring.dao.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class LongKeyGenerator implements KeyGenerator<Long> {
    private AtomicLong nextId = new AtomicLong();

    @Override
    public Long getNextId() {
        return nextId.incrementAndGet();
    }
}
