package org.iproduct.spring.restmvc.dao;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Validated
@Slf4j
@Transactional
public class ArticleRepositoryImpl implements ArticleRepository {
    public static final String INSERT_SQL =
            "INSERT INTO articles(id, title, content, author_id, picture_url, created, updated) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    public void init(DataSource dataSource) {
//        jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Article> findAll() {
        List<Article> articles = this.jdbcTemplate
                .query("select * from articles", new ArticleMapper());
        log.info("Articles loaded: {}", articles.size());
        return articles;
}

    @Override
    public Optional<Article> findById(long id) {
        Article article = this.jdbcTemplate.queryForObject(
                "select * from articles where id = ?", new ArticleMapper(), id);
        log.info("Article found: {}", article);
        return Optional.ofNullable(article);
    }

    @Override
    @Transactional
    public Article insert(@Valid Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"id"});
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
        log.info("Article attempted to be created: {}", article);
        return article;
    }


    @Override
    @Transactional
    public Article save(Article article) {
        int count = this.jdbcTemplate.update(
            "update articles set title = ?, content = ? , author_id = ?, picture_url = ?, created = ?, updated = ? where id = ?",
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
    @Transactional
    public boolean deleteById(long articleId) {
        int count = this.jdbcTemplate.update(
                "delete from articles where id = ?",
                Long.valueOf(articleId));
        return count > 0;
    }

    @Override
    public long count() {
        PreparedStatementCreator p;
        return jdbcTemplate.queryForObject("select count(*) from articles", Long.class);
    }

    @PostConstruct
    public void initDb() {
        log.info("Start data initialization  ...");
        jdbcTemplate.execute("DROP TABLE IF EXISTS articles");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS articles(" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "title VARCHAR(80), " +
                "content VARCHAR(2048), " +
                "author_id BIGINT, " +
                "picture_url VARCHAR(256), " +
                "created TIMESTAMP, " +
                "updated TIMESTAMP" +
                ")");

        int articlesCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM articles", Integer.class);
        log.info("Articles count: {}", articlesCount);

//        if (articlesCount == 0) {
//            List<Article> articles = Arrays.asList(
//                    new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
//                    new Article("Dependency Injection", "Should I use DI or lookup ..."),
//                    new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
//            );
//
//            // Use a Java 8 stream to print out each tuple of the list
//            articles.forEach(article -> {
//                log.info(String.format("Inserting article record for %s %s",
//                        article.getTitle(), article.getContent()));
//                jdbcTemplate.update("INSERT INTO articles(id, title, content, picture_url, created, updated) VALUES (DEFAULT, ?, ?, ?, ?, ?)",
//                        article.getTitle(), article.getContent(), article.getPictureUrl(),
//                        article.getCreated(),
//                        article.getUpdated()
//                );
//            });
//        }

        log.info("Querying for article records where title contains = 'Spring':");
        jdbcTemplate.query(
                "SELECT id, title, content, author_id, picture_url, created, updated FROM articles WHERE title LIKE ?",
                (rs, rowNum) -> new Article(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getLong("author_id"),
                        rs.getString("picture_url"),
                        rs.getTimestamp("created").toLocalDateTime(),
                        rs.getTimestamp("updated").toLocalDateTime()),
               "%Spring%"
        ).forEach(article -> log.info(article.toString()));
    }

    private static final class ArticleMapper implements RowMapper<Article> {
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
}
