package course.spring.blogs.dao.impl;

import course.spring.blogs.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getBoolean("active"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getTimestamp("modified").toLocalDateTime()
        );
    }
}
