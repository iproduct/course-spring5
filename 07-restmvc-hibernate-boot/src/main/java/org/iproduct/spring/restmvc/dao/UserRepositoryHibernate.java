package org.iproduct.spring.restmvc.dao;

import org.hibernate.SessionFactory;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryHibernate implements UserRepository {

    private TransactionTemplate transactionTemplate;
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> findAll() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select user from User user", User.class)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return this.sessionFactory.getCurrentSession()
                .byId(User.class).loadOptional(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select user from User user where user.username = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }

    @Override
    public long count() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select count(user) from User user", Long.class)
                .uniqueResult();
    }

    @Override
    public User insert(User user) {
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                sessionFactory.getCurrentSession()
                        .persist(user);
            }
        });

        return user;
    }

    @Override
    @Transactional
    public User save(User user) {
        Optional<User> toBeDeleted = findById(user.getId());
        if (!toBeDeleted.isPresent()) {
            throw new EntityNotFoundException("User with ID=" + user.getId() + " does not exist.");
        }
       return (User) this.sessionFactory.getCurrentSession()
                .merge(user);
    }

    @Override
    @Transactional
    public Optional<User> deleteById(long userId) {
        Optional<User> toBeDeleted = findById(userId);
        if (!toBeDeleted.isPresent()) {
            throw new EntityNotFoundException("User with ID=" + userId + " does not exist.");
        }
        this.sessionFactory.getCurrentSession()
                .delete(toBeDeleted);
        return toBeDeleted;
    }
}
