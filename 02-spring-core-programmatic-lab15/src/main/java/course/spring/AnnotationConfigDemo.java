package course.spring;

import course.spring.config.AppConfiguration;
import course.spring.service.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ArticlePresenter presenter1 = ctx.getBean("presenter", ArticlePresenter.class);
        presenter1.present();
//        ArticlePresenter presenter2 = ctx.getBean("presenter", ArticlePresenter.class);
//        presenter2.present();
//        System.out.println(presenter1);
//        System.out.println(presenter2);
    }
}
