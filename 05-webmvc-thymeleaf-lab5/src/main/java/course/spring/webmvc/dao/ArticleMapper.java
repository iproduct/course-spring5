package course.spring.webmvc.dao;

import course.spring.webmvc.model.Article;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleMapper implements RowMapper<Article> {
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Article(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getLong("author_id"),
                rs.getString("picture_url"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getTimestamp("updated").toLocalDateTime());
    }
}
