package org.iproduct.spring.demo.beanconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloWorldSpringAnnotated {
    public static void main(String... args) {
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.iproduct.spring.demo.beanconfig");
        ArticlePresenter mr = ctx.getBean("presenter", ArticlePresenter.class);
        mr.present();
    }
}