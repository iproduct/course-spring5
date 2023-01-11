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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.hibernate.spring.entity.Role.*;

@Component
@Slf4j
public class MappingDemo4 implements ApplicationRunner {
    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "Admin123&",
                    Set.of(READER, AUTHOR, ADMIN), "+1-234-5678"),
            new User("Default", "Author", "author", "Author123&",
                    Set.of(READER, AUTHOR), "+40-123-4567"),
            new User("Default", "Reader", "reader", "Reader123&",
                    Set.of(READER), "+359-123-4567")
    );
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Country> countries = List.of(
                new Country(1, "United States"),
                new Country(40, "Romania"),
                new Country(359, "Bulgaria")
        );
        template.executeWithoutResult(status -> {
            List<Country> results = countries.stream().map(c -> {
                entityManager.persist(c);
                return c;
            }).collect(Collectors.toList());
            log.info("!!! Countries: {}", results);
        });

        List<User> users = template.execute(status ->
            SAMPLE_USERS.stream().map(u -> {
                entityManager.persist(u);
                return u;
            }).collect(Collectors.toList())
        );
        log.info("!!! Users: {}", users);

        template.executeWithoutResult(status -> {
            List<User> fetchedUsers = entityManager.createQuery("SELECT u FROM User u")
                    .getResultList();
            for(User u : fetchedUsers) {
                log.info("!!!!! User: {} -> Country: {}", u, u.getCountry());
            }
        });
    }
}
