package course.spring.blogs.dao.impl;

import course.spring.blogs.entity.Post;
import lombok.NonNull;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PostMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Post(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getLong("author_id"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getTimestamp("modified").toLocalDateTime()
        );
    }
}
