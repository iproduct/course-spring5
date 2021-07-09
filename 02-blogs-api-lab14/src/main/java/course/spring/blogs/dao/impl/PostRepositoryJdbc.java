package course.spring.blogs.dao.impl;

import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryJdbc implements PostRepository {
    public static final String SELECT_SQL = "SELECT * FROM posts;";
    public static final String COUNT_SQL = "SELECT COUNT(*) from posts;";
    public static final String INSERT_SQL = "INSERT INTO posts(id, title, content, author_id, created, modified)" +
            " VALUES (DEFAULT, :title, :content, :authorId, :created, :modified);";
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepositoryJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = jdbcTemplate.query(SELECT_SQL, new PostMapper());
        return posts;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Post create(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParams = new BeanPropertySqlParameterSource(post);
        jdbcTemplate.update(INSERT_SQL, namedParams, keyHolder, new String[] {"id"});
        post.setId(keyHolder.getKey().longValue());
        return post;
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
        return jdbcTemplate.queryForObject(COUNT_SQL, new MapSqlParameterSource(), Long.class);
    }
}
