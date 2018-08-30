package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.Article;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArticleMapper implements RowMapper<Article> {
    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Article(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("picture_url"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getTimestamp("updated").toLocalDateTime());
    }
}
