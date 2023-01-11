package course.hibernate.service;

import course.hibernate.entity.Account;
import course.hibernate.entity.AccountSummary;
import course.hibernate.entity.AccountTransaction;
import course.hibernate.entity.Client;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class BankService {
    @PersistenceContext
    private EntityManager em;

    public AccountSummary openAccount(Long clientNumber, String fName, String lName, Long accNumber,
                                      String description, int amountCents, String purpose) {
        Client client = new Client(clientNumber, fName, lName);
        em.persist(client);

        Account account = new Account(accNumber, client, description);
        client.getAccounts().add(account);
        em.persist(account);

        AccountTransaction transaction = new AccountTransaction(account, amountCents, purpose);
        em.persist(transaction);

        AccountSummary summary = em.createQuery("select s from AccountSummary s where s.id = :id", AccountSummary.class)
                .setParameter("id", account.getId())
                .getSingleResult();
        return summary;
    }

    public AccountSummary updateAccountWithTransaction(Long accNumber, int amountCents, String purpose) {
        AccountTransaction transaction = new AccountTransaction(em.getReference(Account.class, accNumber), amountCents, purpose);
        em.persist(transaction);

        AccountSummary summary = em.find(AccountSummary.class, accNumber);
        return summary;
    }

}
