package course.spring.core;

import course.spring.core.service.ArticleConsumer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("course.spring.core");
        var consumer = ctx.getBean(ArticleConsumer.class);
        consumer.consume();
    }
}
