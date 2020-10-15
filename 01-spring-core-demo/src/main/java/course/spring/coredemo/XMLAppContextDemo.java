package course.spring.coredemo;

import course.spring.coredemo.consumer.ArticleConsumer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XMLAppContextDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-config.xml");
        ArticleConsumer consumer = ctx.getBean(ArticleConsumer.class);
        consumer.consume();
    }
}
