package course.hibernate.spring.service.impl;

import course.hibernate.spring.dao.UserRepository;
import course.hibernate.spring.entity.User;
import course.hibernate.spring.events.UserCreationEvent;
import course.hibernate.spring.exception.EntityNotFoundException;
import course.hibernate.spring.exception.InvalidClientDataException;
import course.hibernate.spring.service.UserService;
import course.hibernate.spring.util.ExceptionHandlingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    // single TransactionTemplate shared amongst all methods in this instance
    private final PlatformTransactionManager transactionManager;
    // single TransactionTemplate shared amongst all methods in this instance
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            ApplicationEventPublisher applicationEventPublisher,
            PlatformTransactionManager transactionManager,
            TransactionTemplate transactionTemplate) {
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.transactionManager = transactionManager;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with ID=%s not found.", id)));
    }

    @Override
    public List<User> findByIds(List<Long> ids) {
        return userRepository.findByIds(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with username='%s' not found.")));
    }

    @Override
    public User create(User user) {
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            return userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            throw new InvalidClientDataException("Database constraint invalidated", ex,
                    ExceptionHandlingUtils.extractViolations(ex));
        }
    }

    @Override
    public User update(User user) {
        User old = findById(user.getId());
        if(!old.getUsername().equals(user.getUsername())) {
            throw new InvalidClientDataException("Username can not be changed");
        }
        user.setCreated(old.getCreated());
        user.setPassword(old.getPassword());
        user.setModified(LocalDateTime.now());
        try {
            return userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            throw new InvalidClientDataException("Database constraint invalidated", ex,
                    ExceptionHandlingUtils.extractViolations(ex));
        }
    }

    @Override
    @Transactional //(noRollbackFor = EntityNotFoundException.class)
    public User deleteById(Long id) {
        User old = findById(id);
        userRepository.deleteById(id);
        return old;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return userRepository.count();
    }

    @Transactional
    public List<User> createBatch(List<User> users) {
        List<User> created =  userRepository.saveAll(users).stream()
                .map(user -> {
                    applicationEventPublisher.publishEvent(new UserCreationEvent(user));
                    return user;
                })
                .collect(Collectors.toList());
        return created;
    }

//    Programmatic transaction
//    public List<User> createBatch(List<User> users) {
//        // the code in this method executes in a transactional context
//        return transactionTemplate.execute(status -> {
//            List<User> created = users.stream()
//                    .map(user -> {
//                        try {
//                            User newUser = create(user);
//                            applicationEventPublisher.publishEvent(new UserCreationEvent(newUser));
//                            return newUser;
//                        } catch (ConstraintViolationException ex) {
//                            log.error(">>> Constraint violation inserting users: {} - {}", user, ex.getMessage());
//                            status.setRollbackOnly();
//                            return null;
//                        }
//                    }).collect(Collectors.toList());
//            return created;
//        });
//    }

//    Managing transaction directly using PlatformTransactionManager
//    public List<User> createBatch(List<User> users) {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        // explicitly setting the transaction name is something that can only be done programmatically
//        def.setName("createUsersBatchTransaction");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        def.setTimeout(5); // 5 seconds
//
//        // Do in transaction
//        TransactionStatus status = transactionManager.getTransaction(def);
//        List<User> created = users.stream()
//            .map(user -> {
//                try {
//                    User resultUser = create(user);
//                    applicationEventPublisher.publishEvent(new UserCreationEvent(resultUser));
//                    return resultUser;
//                } catch (ConstraintViolationException ex) {
//                    log.error(">>> Constraint violation inserting user: {} - {}", user, ex.getMessage());
//                    transactionManager.rollback(status); // ROLLBACK
//                    throw ex;
//                }
//            }).collect(Collectors.toList());
//
//        transactionManager.commit(status); // COMMIT
//        return created;
//    }

    @TransactionalEventListener
    public void handleUserCreatedTransactionCommit(UserCreationEvent creationEvent) {
        log.info(">>> Transaction COMMIT for user: {}", creationEvent.getUser());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleUserCreatedTransactionRollaback(UserCreationEvent creationEvent) {
        log.info(">>> Transaction ROLLBACK for user: {}", creationEvent.getUser());
    }
}
