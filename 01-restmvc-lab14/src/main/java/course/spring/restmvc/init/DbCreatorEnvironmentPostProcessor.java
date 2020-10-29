package course.spring.restmvc.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DbCreatorEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String dbUrl = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String dbDriver = environment.getProperty("spring.datasource.driver-class-name");

        Pattern dbUrlWithoutDbNamePattern = Pattern.compile("((.*)//([\\w.]+)(:\\d+)?)/(.*)");
        Matcher matcher = dbUrlWithoutDbNamePattern.matcher(dbUrl);
        if(matcher.matches() && matcher.groupCount() >= 5) {
            String dbUrlWithoutDbName = matcher.group(1);
            String dbName = matcher.group(5);
            try(Connection connection = DriverManager.getConnection(dbUrlWithoutDbName + "/postgres", username, password);
                PreparedStatement ps = connection.prepareStatement("CREATE DATABASE " + dbName)) {
                ps.execute();
            } catch(SQLException ex) {
                log.error(String.format("Error creating database '%s'.", dbName), ex);
            }
        }
    }
}
