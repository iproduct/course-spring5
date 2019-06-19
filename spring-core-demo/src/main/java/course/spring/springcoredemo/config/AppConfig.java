package course.spring.springcoredemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:articles.properties")
public class AppConfig {

}
