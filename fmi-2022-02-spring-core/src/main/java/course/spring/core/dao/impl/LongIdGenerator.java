package course.spring.core.dao.impl;

import course.spring.core.dao.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

//@Component
//@Scope("prototype")
//@PropertySource("classpath:article.properties")
public class LongIdGenerator implements IdGenerator<Long> {
    private AtomicLong sequence;
//    @Value("${initialGeneratedId}")
    private long initialId;
//    @Autowired
//    private Environment environment;

    @PostConstruct
    public void init() {
//        initialId = Integer.parseInt(environment.getProperty("initialGeneratedId"));
        this.sequence =  new AtomicLong(initialId);
    }


    @Override
    public Long getNextId() {
        return sequence.incrementAndGet();
    }
}
