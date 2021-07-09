package course.spring.blogs.dao.impl;

import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.User;
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
public class UserRepositoryJdbc implements UserRepository {
    public static final String SELECT_SQL = "SELECT * FROM users;";
    public static final String COUNT_SQL = "SELECT COUNT(*) from users;";
    public static final String INSERT_SQL =
            "INSERT INTO users(id, first_name, last_name, username, password, email, active, created, modified)" +
                    " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?);";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        List<User> users = jdbcTemplate.query(SELECT_SQL, new UserMapper());
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setBoolean(6, user.isActive());
            ps.setTimestamp(7, Timestamp.valueOf(user.getCreated()));
            ps.setTimestamp(8, Timestamp.valueOf(user.getModified()));
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User deleteById(Long id) {
        return null;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Long.class);
    }
}
