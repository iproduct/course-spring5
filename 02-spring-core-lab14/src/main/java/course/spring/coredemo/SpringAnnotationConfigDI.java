package course.spring.coredemo;

import course.spring.coredemo.service.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAnnotationConfigDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("course.spring.coredemo");
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class); // Service locator pattern
        presenter.present();
    }
}
