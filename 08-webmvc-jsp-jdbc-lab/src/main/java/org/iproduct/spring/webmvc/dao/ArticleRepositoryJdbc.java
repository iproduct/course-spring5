package org.iproduct.spring.webmvc.dao;

import org.iproduct.spring.webmvc.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ArticleRepositoryJdbc implements ArticleRepository {
    private static final Logger log = LoggerFactory.getLogger(ArticleRepositoryJdbc.class);
    private static final String INSERT_SQL =
            "INSERT INTO articles(title, content, created_date, picture_url) VALUES (?, ?, ?, ?)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<Article> findAll() {
        List<Article> articles = jdbcTemplate.query("select * from  articles", new ArticleMapper());
        log.info("Loaded articles: {}", articles.size());
        return articles;
    }

    @Override
    public Article find(long id) {
        return null;
    }

    @Override
    public Article create(Article article) {
        return null;
    }

    @Override
    public Article update(Article article) {
        return null;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @PostConstruct
    private void init() {
        log.info("Start data initialization ...");
//        jdbcTemplate.execute("DROP TABLE articles");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS articles(" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(80), " +
                "content VARCHAR(512), " +
                "created_date TIMESTAMP, " +
                "picture_url VARCHAR(80))");
        int articlesCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM articles", Integer.class);
        if(articlesCount == 0) {
            List<Article> articles = Arrays.asList(
                    new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
                    new Article("Dependency Injection", "Should I use DI or lookup ..."),
                    new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
            );
            articles.forEach( a -> {
                log.info("Inserting article record: {}, {}", a.getTitle(), a.getContent());
                jdbcTemplate.update(INSERT_SQL, a.getTitle(), a.getContent(),
                        a.getCreatedDate(), a.getPictureUrl());
            });
        }
        log.info("Quering for articles where title contains 'Spring'");
        jdbcTemplate.query("SELECT * FROM articles WHERE title LIKE ?",
                new Object[] {"%Spring%"}, new ArticleMapper())
                .forEach(article -> log.info(article.toString()));
    }

    private class ArticleMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Article(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_date"),
                    rs.getString("picture_url")
            );
        }
    }
}
