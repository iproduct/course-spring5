package coredemo;

import coredemo.domain.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationSpringDI {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext("coredemo");
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
