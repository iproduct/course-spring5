package ws.spring.dao.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ws.spring.dao.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Scope("prototype")
public class LongIdGenerator implements IdGenerator<Long> {
    private static AtomicLong nextId = new AtomicLong();

    @Override
    public Long getNextId() {
        return nextId.incrementAndGet();
    }
}
