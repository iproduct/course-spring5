package course.hibernate.spring.init;

import course.hibernate.spring.entity.City;
import course.hibernate.spring.entity.Person;
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
public class MappingDemo2 implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
            City _NewYork = new City("New York");
            entityManager.persist(_NewYork);
            City _Seattle = new City("Seattle");
            entityManager.persist(_Seattle);
            City _Sofia = new City("Sofia");
            entityManager.persist(_Sofia);

            Person person1 = new Person(1L, "John Doe", "Atlantis");
            entityManager.persist(person1);
            Person person2 = new Person(2L, "Maria Petrova", "Sofia");
            entityManager.persist(person2);
        });
        template.executeWithoutResult(status -> {
            Person p1 = entityManager.find(Person.class, 1L);
            log.info("!!!!! Person: {} from City: {}", p1.getName(), p1.getCity());
            Person p2 = entityManager.find(Person.class, 2L);
            log.info("!!!!! Person: {} from City: {}", p2.getName(), p2.getCity());
        });
    }
}
