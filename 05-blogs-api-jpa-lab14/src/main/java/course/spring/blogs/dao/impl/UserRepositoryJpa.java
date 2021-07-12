package course.spring.blogs.dao.impl;

import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.User;
import course.spring.blogs.exception.NonexistingEntityException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryJpa implements UserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public User create(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        User removed = findById(user.getId()).orElseThrow(() ->
                new NonexistingEntityException(String.format("Entity with ID=%s does not exist", user.getId())));
        return em.merge(user);
    }

    @Override
    public User deleteById(Long id) {
        User removed = findById(id).orElseThrow(() ->
                new NonexistingEntityException(String.format("Entity with ID=%s does not exist", id)));
        em.remove(id);
        return removed;
    }

    @Override
    public long count() {
        return em.createQuery("select count(u) from User u", Long.class)
                .getSingleResult();
    }
}
