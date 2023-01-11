package course.hibernate.spring.init;

import course.hibernate.spring.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Component
@Slf4j
public class MappingDemo3 implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
            IntegerProperty age = new IntegerProperty(1L, "age", 23);
            entityManager.persist(age);
            StringProperty name = new StringProperty(1L, "name", "John Doe");
            entityManager.persist(name);

            PropertyHolder holder1 = new PropertyHolder(1L);
            holder1.getProperties().add(age);
            holder1.getProperties().add(name);
            entityManager.persist(holder1);

//            PropertyHolder holder2 = new PropertyHolder(2L, name);
//            entityManager.persist(holder2);
        });
        template.executeWithoutResult(status -> {
            PropertyHolder p1= entityManager.find(PropertyHolder.class, 1L);
            for(Property p : p1.getProperties()) {
                log.info("!!!!! Prop Name: {}, Value: {}, Type: {}",
                        p.getName(), p.getValue(), p.getClass().getSimpleName());
            }
//            PropertyHolder p2= entityManager.find(PropertyHolder.class, 2L);
//            log.info("!!!!! Prop Name: {}, Value: {}, Type: {}",
//                    p2.getProperty().getName(), p2.getProperty().getValue(), p2.getClass().getSimpleName());
        });
    }
}
