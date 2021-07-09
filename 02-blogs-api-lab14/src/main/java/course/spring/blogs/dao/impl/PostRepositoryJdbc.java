package course.spring.blogs.dao.impl;

import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
            " VALUES (DEFAULT, ?, ?, ?, ?, ?);";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepositoryJdbc(JdbcTemplate jdbcTemplate) {
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
        jdbcTemplate.update((Connection connection) -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, post.getTitle());
                    ps.setString(2, post.getContent());
                    ps.setLong(3, post.getAuthorId());
                    ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
                    ps.setTimestamp(5, Timestamp.valueOf(post.getModified()));
                    return ps;
                }, keyHolder);
            post.setId(keyHolder.getKey().longValue());
            return post;
        }

        @Override
        public Post update (Post post){
            return null;
        }

        @Override
        public Post deleteById (Long id){
            return null;
        }

        @Override
        public long count () {
            return jdbcTemplate.queryForObject(COUNT_SQL, Long.class);
        }
    }
