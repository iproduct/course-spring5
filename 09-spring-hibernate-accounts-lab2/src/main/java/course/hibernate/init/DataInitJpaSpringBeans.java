package course.hibernate.init;

import course.hibernate.entity.AccountSummary;
import course.hibernate.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Slf4j
public class DataInitJpaSpringBeans implements ApplicationRunner {
    @PersistenceContext
    private EntityManager em;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final BankService bankService;

    @Autowired
    public DataInitJpaSpringBeans(ApplicationEventPublisher applicationEventPublisher, BankService bankService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.bankService = bankService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AccountSummary summary1 = bankService.openAccount(1L, "John", "Doe", 1L, "Checking account",
                7000 * 100, "Salary");
        log.info("ACCOUNT SUMMARY for [{}] - balance: {}", summary1.getClientName(), summary1.getBalance() / 100.0);
        AccountSummary summary2 = bankService.updateAccountWithTransaction(1L, -1400 * 100, "Electricity bills.");
        log.info("ACCOUNT SUMMARY for [{}] - balance: {}", summary2.getClientName(), summary2.getBalance() / 100.0);
    }
}
