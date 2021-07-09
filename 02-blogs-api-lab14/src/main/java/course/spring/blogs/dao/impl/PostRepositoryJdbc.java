package course.spring.blogs.dao.impl;

import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.Optional;

public class PostRepositoryJdbc implements PostRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Post> findAll() {
        return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Post create(Post post) {
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public Post deleteById(Long id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
