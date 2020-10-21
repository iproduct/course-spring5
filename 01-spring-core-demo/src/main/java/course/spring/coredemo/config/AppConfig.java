package course.spring.coredemo.config;

import course.spring.coredemo.AnnotationAppContextDemo;
import course.spring.coredemo.consumer.ArticleConsumer;
import course.spring.coredemo.consumer.ConsoleArticleConsumer;
import course.spring.coredemo.formatter.ArticleFormatter;
import course.spring.coredemo.formatter.DefaultArticleFormatter;
import course.spring.coredemo.provider.ArticleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackageClasses = AnnotationAppContextDemo.class)
@PropertySource("article.properties")
public class AppConfig {
    @Bean(name="formatter")
    public ArticleFormatter createFormatter() {
        return new DefaultArticleFormatter();
    }

    @Bean("presenter")
    public ArticleConsumer createConsumer(
            ArticleProvider provider,
            ArticleFormatter formater,
            @Value("${message}") String message) {
        ConsoleArticleConsumer consumer =  new ConsoleArticleConsumer(provider);
        consumer.setFormatter(formater);
        consumer.setMessage(message);
        return consumer;
    }
}
