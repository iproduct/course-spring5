package org.iproduct.spring.programmatic;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;

public class SpringAnnotationsDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.iproduct.spring.programmatic");
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
