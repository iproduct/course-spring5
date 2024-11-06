package course.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:posts.properties")
@ComponentScan(basePackageClasses = {course.spring.Main.class})
public class AppConfig {
}
