package course.spring.webmvc.dao;

import course.spring.webmvc.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class ArticleRepositoryJdbc implements ArticlesRepository {
    public static final String INSERT_SQL =
            "INSERT INTO articles2(id, title, content, author_id, picture_url, created, updated) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Article> findAll() {
        List<Article> articles = jdbcTemplate
                .query("select * from articles2", new ArticleMapper());
        log.info("Articles loaded: {}", articles.size());
        return articles;
    }

    @Override
    public Optional<Article> findById(Long id) {
        Article article = this.jdbcTemplate.queryForObject(
                "select * from articles2 where id = ?",
                new Object[]{id}, new ArticleMapper());
        log.info("Article found: {}", article);
        return Optional.of(article);
    }

    @Override
    @Transactional
    public Article insert(@Valid Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                ps.setString(1, article.getTitle());
                ps.setString(2, article.getContent());
                ps.setLong(3, article.getAuthorId());
                ps.setString(4, article.getPictureUrl());
                ps.setTimestamp(5, new Timestamp(article.getCreated().toInstant(ZoneOffset.UTC).toEpochMilli()));
                ps.setTimestamp(6, new Timestamp(article.getCreated().toInstant(ZoneOffset.UTC).toEpochMilli()));

                return ps;
            }
        }, keyHolder);
        article.setId(keyHolder.getKey().longValue());
        log.info("Article created: {}", article);
        return article;
    }

    @Override
    public Article save(Article article) {
        int count = this.jdbcTemplate.update(
                "update articles2 set (title, content, author_id, picture_url, created, updated) = (?, ?, ?, ?, ?, ?) where id = ?",
                article.getTitle(),
                article.getContent(),
                article.getAuthorId(),
                article.getPictureUrl(),
                article.getCreated(),
                article.getUpdated(),
                article.getId());
        log.info("Article updated: {}", article);
        return article;
    }

    @Override
    public boolean deleteById(Long articleId) {
        int count = this.jdbcTemplate.update(
                "delete from articles2 where id = ?",
                Long.valueOf(articleId));
        return count > 0;
    }

    @Override
    public long count() {
        PreparedStatementCreator p;
        return jdbcTemplate.queryForObject("select count(*) from articles2", Long.class);
    }

}
