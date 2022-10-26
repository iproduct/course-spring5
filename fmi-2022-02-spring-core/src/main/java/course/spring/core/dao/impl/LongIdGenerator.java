package course.spring.core.dao.impl;

import course.spring.core.dao.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@Component
@PropertySource("classpath:article.properties")
public class LongIdGenerator implements IdGenerator<Long> {
    private AtomicLong sequence;
    @Value("${initialArticleId}")
    private long initialId;

    @PostConstruct
    public void init() {
        this.sequence =  new AtomicLong(initialId);
    }


    @Override
    public Long getNextId() {
        return sequence.incrementAndGet();
    }
}
