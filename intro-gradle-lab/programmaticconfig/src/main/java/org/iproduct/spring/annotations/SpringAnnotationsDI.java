package org.iproduct.spring.annotations;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@ComponentScan(basePackageClasses = org.iproduct.spring.annotations.SpringAnnotationsDI.class)
public class SpringAnnotationsDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new GenericApplicationContext();
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
