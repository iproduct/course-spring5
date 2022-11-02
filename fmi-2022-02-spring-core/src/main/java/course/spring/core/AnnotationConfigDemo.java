package course.spring.core;

import course.spring.core.service.Consumer;
//import course.spring.core.service.UserConsumer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("course.spring.core");
        var articleConsumer = ctx.getBean("articleConsumer", Consumer.class);
        articleConsumer.consume();
        var userConsumer = ctx.getBean("userConsumer", Consumer.class);
        userConsumer.consume();
    }
}
