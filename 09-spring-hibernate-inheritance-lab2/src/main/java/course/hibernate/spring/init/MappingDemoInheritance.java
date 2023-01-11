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
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.hibernate.spring.entity.Role.*;

@Component
@Slf4j
public class MappingDemoInheritance implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
            DebitAccount debitAccount = new DebitAccount(1001L, "John Doe", BigDecimal.valueOf(100),
                    BigDecimal.valueOf(1.5D), BigDecimal.valueOf(25));
            CreditAccount creditAccount = new CreditAccount(2001L, "John Doe", BigDecimal.valueOf(1000),
                    BigDecimal.valueOf(1.9D), BigDecimal.valueOf(5000));
            Account otherAccount = new Account(4920L, "John Doe", BigDecimal.valueOf(1000),
                    BigDecimal.valueOf(1.9D));
            entityManager.persist(debitAccount);
            entityManager.persist(creditAccount);
            entityManager.persist(otherAccount);
        });

        template.executeWithoutResult(status -> {
            List<Account> fetchedAccounts = entityManager.createQuery("SELECT a FROM Account a")
                    .getResultList();
            for(Account a : fetchedAccounts) {
                log.info("!!!!! Acount: {} [{}]", a, a.getClass().getSimpleName());
            }
        });
    }
}
