package course.ws.dao.impl;

import course.ws.dao.IdGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Scope("prototype")
public class LongIdGenerator implements IdGenerator<Long> {
    private AtomicLong idGenerator = new AtomicLong();

    @Override
    public Long getNextId() {
        return idGenerator.incrementAndGet();
    }
}
