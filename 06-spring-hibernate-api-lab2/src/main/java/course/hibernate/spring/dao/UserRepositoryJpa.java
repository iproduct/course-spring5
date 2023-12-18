package course.hibernate.spring.dao;

import course.hibernate.spring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@Transactional
public class UserRepositoryJpa extends SimpleJpaRepository<User, Long> implements UserRepository {
    private EntityManager entityManager;

    @Autowired
    public UserRepositoryJpa(EntityManager em) {
        super(User.class, em);
        entityManager = em;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
//        return Optional.ofNullable(entityManager.find(User.class, id));
        return entityManager.unwrap(Session.class).byId(User.class).loadOptional(id);
    }

    @Override
//    @Transactional(readOnly = true)
    public List<User> findByIds(List<Long> ids) {
//        return Optional.ofNullable(entityManager.find(User.class, id));
        try {
            deleteById(1L);
        }catch (Exception ex){
            log.info("Entity already deleted.");
        }
        return entityManager.unwrap(Session.class).byMultipleIds(User.class)
                .enableOrderedReturn(true)
                .enableSessionCheck(false)
                .enableReturnOfDeletedEntities(true)
                .with(CacheMode.NORMAL)
        .multiLoad(ids);
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return entityManager.createQuery("select u from User u join fetch u.roles where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream().findFirst();
    }

    @Override
    public User save(User entity) {
        if (entity.getId() != null) {
            return update(entity);
        } else {
            return create(entity);
        }
    }


    @Override
    public User create(User user) {
//        entityManager.persist(user);
        Long id = (Long) entityManager.unwrap(Session.class).save(user);
        log.info("Saved User ID = {}, {}", id, user);
        return user;
    }

    @Override
    public User update(User user) {
        User old = findById(user.getId()).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User with ID='%s' does not exist.", user.getId())));
        log.info("!!! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n Updating user in UserRepository: {}", System.identityHashCode(user));
//        entityManager.unwrap(Session.class).evict(old);
//
//        old.setUsername(user.getUsername());
//        old.setFirstName(user.getFirstName());
//        old.setLastName(user.getLastName());
//        old.setRoles(user.getRoles());
//        old.setModified(user.getModified());

        var result = entityManager.merge(user);
//        try {
//            entityManager.unwrap(Session.class).clear();
//        } catch (Exception ex){
//            log.error("!!!Error during update", ex);
//            throw ex;
//        }
        log.info("!!! Updated user: {} -> {}", System.identityHashCode(result), result);
        return result;
    }


    @Override
    public void deleteById(Long id) {
        User old = findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User with ID='%s' does not exist.", id)));
        entityManager.remove(old); //.unwrap(Session.class).delete();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return entityManager.createQuery("select count(u) from User u", Long.class)
                .getSingleResult();
    }


}
