package course.spring.coredemo;

import course.spring.coredemo.service.ArticlePresenter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class SpringAnnotationConfigDI {
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext("course.spring.coredemo");
        ctx.registerShutdownHook();
        ArticlePresenter presenter1 = ctx.getBean("presenter", ArticlePresenter.class); // Service locator pattern
        ArticlePresenter presenter2 = ctx.getBean("presenter", ArticlePresenter.class); // Service locator pattern
        System.out.printf("Presenter1: %s, Presenter2: %s%n", presenter1.hashCode(), presenter2.hashCode());
        presenter1.present();
    }
}
