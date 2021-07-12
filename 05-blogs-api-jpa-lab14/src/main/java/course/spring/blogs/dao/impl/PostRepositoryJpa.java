package course.spring.blogs.dao.impl;

import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.entity.Post;
import course.spring.blogs.exception.NonexistingEntityException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(timeout = 5)
public class PostRepositoryJpa implements PostRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(em.find(Post.class, id));
    }

    @Override
    public Post create(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public Post update(Post post) {
        Post removed = findById(post.getId()).orElseThrow(() ->
                new NonexistingEntityException(String.format("Entity with ID=%s does not exist", post.getId())));
        return em.merge(post);
    }

    @Override
    public Post deleteById(Long id) {
        Post removed = findById(id).orElseThrow(() ->
                new NonexistingEntityException(String.format("Entity with ID=%s does not exist", id)));
        em.remove(id);
        return removed;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return em.createQuery("select count(p) from Post p", Long.class)
                .getSingleResult();
    }
}
