package org.iproduct.spring.registration.dao;

import org.iproduct.spring.registration.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Component
public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("fname"),
                rs.getString("lname"),
                rs.getString("email"),
                rs.getString("roles"),
                rs.getBoolean("active"),
                new Date(rs.getTimestamp("created").getTime()),
                new Date(rs.getTimestamp("updated").getTime()));
    }
}

