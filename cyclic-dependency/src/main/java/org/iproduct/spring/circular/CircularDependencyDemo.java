package org.iproduct.spring.circular;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class CircularDependencyDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(
                "org.iproduct.spring.circular");
        BeanB b = ctx.getBean(BeanB.class);
        System.out.println(b.getBeanA().getContent());
    }
}
