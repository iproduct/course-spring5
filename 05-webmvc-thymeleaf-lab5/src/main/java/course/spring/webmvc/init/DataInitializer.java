package course.spring.webmvc.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer  implements CommandLineRunner {
    public static final String DROP_ARTICLES_TABLE = "DROP TABLE IF EXISTS articles2";
    public static final String CREATE_ARTICLES_TABLE =
            "CREATE TABLE IF NOT EXISTS articles2(" +
                    "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(80), " +
                    "content VARCHAR(512), " +
                    "author_id BIGINT, " +
                    "picture_url VARCHAR(80)," +
                    "created TIMESTAMP, " +
                    "updated TIMESTAMP" +
                    ")";
    public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users2";
    public static final String CREATE_USERS_TABLE =
           "CREATE TABLE IF NOT EXISTS users2(" +
                    "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(40), " +
                    "password VARCHAR(80), " +
                    "fname VARCHAR(40), " +
                    "lname VARCHAR(40), " +
                    "roles VARCHAR(80), " +
                    "active BOOLEAN, " +
                    "created TIMESTAMP, " +
                    "updated TIMESTAMP" +
                    ")";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String[] args) throws Exception {
        log.info("Starting data initialization ...");
//        jdbcTemplate.execute(DROP_ARTICLES_TABLE);
        jdbcTemplate.execute(CREATE_ARTICLES_TABLE);
        //jdbcTemplate.execute(DROP_USERS_TABLE);
        jdbcTemplate.execute(CREATE_USERS_TABLE);
    }
}
