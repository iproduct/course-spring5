package course.hibernate.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class ContactEventListener {
    @TransactionalEventListener
    public void handleUserCreatedTransactionCommit(ContactCreationEvent creationEvent) {
//        log.info(">>> Transaction COMMIT for Contact: {}", creationEvent.getContact());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleUserCreatedTransactionRollaback(ContactCreationEvent creationEvent) {
//        log.info(">>> Transaction ROLLBACK for Contact: {}", creationEvent.getContact());
    }
}
