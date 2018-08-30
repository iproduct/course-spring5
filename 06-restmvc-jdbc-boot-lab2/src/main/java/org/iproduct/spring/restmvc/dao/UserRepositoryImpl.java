package org.iproduct.spring.restmvc.dao;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    public static final String INSERT_SQL =
            "INSERT INTO users(id, username, password, fname, lname, roles, active, created, updated) VALUES " +
                    "(DEFAULT, :username, :password, :fname, :lname, :roles, :active, :created, :updated)";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;


    @Override
    public Collection<User> findAll() {
        List<User> users = jdbcTemplate.query("select * from users", userMapper);
        log.info("Users loaded: {}", users);
        return users;
    }

    @Override
    public Optional<User> findById(long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        User user = jdbcTemplate.queryForObject("select * from users where id = :id",
               namedParameters, userMapper);
        log.info("Article created: {}", user);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("username", username);
        try {
            User user = this.jdbcTemplate.queryForObject(
                    "select * from users where username = :username", namedParameters, userMapper);
            log.info("User found: {}", user);
            return Optional.of(user);
        } catch(IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }

    }

    @Override
    @Transactional
    public User insert(@Valid User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
        jdbcTemplate.update(INSERT_SQL, namedParameters, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        log.info("User created: {}", user);
        return user;
    }


    @Override
    public User save(User user) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
        int count = this.jdbcTemplate.update(
                "update users set (username, password, fname, lname, roles, active, created, updated) = " +
                        "(:username, :password, :fname, :lname, :roles, :active, :created, :updated) where id = :id",
                namedParameters);
        log.info("User updated: {}", user);
        return user;
    }

    @Override
    public boolean deleteById(long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        int count = this.jdbcTemplate.update(
                "delete from users where id = ?", namedParameters);
        return count > 0;
    }

    @Override
    public long count() {
        PreparedStatementCreator p;
        return jdbcTemplate.getJdbcOperations().queryForObject("select count(*) from users", Long.class);
    }
}
