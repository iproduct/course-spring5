package course.spring.coredemo;

import course.spring.coredemo.config.AppConfig;
import course.spring.coredemo.consumer.ArticleConsumer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationAppContextDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ArticleConsumer consumer = ctx.getBean("presenter", ArticleConsumer.class);
        consumer.consume();
    }
}
