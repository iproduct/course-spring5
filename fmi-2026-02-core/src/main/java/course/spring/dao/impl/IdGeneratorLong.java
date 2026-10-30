package course.spring.dao.impl;

import course.spring.dao.IdGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

//@Component("longIdGen")
//@Scope("prototype")
public class IdGeneratorLong implements IdGenerator<Long> {
    private AtomicLong nextId;

    @Value("${initialGeneratedId:0}")
    private Long initialGeneratedId = 0L;

    public IdGeneratorLong() {
        nextId = new AtomicLong(initialGeneratedId);
    }
    public IdGeneratorLong(long initialValue) {
        nextId = new AtomicLong(initialValue);
    }

    @Override
    public Long getNextId() {
        return nextId.incrementAndGet();
    }
}
