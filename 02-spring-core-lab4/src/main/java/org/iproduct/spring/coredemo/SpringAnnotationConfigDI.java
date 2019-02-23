package org.iproduct.spring.coredemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAnnotationConfigDI {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(
                ProgrammaticAnnotationConfig.class
        );

        ArticlePresenter presenter =
                ctx.getBean("presenter", ArticlePresenter.class);

        presenter.present();
    }
}
