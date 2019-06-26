package course.spring.springcoredemo;

import course.spring.springcoredemo.config.AppConfig;
import course.spring.springcoredemo.domain.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationContextDemo {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);
        ArticlePresenter presenter = ctx.getBean( ArticlePresenter.class);
        System.out.println(presenter.getTitle());
        presenter.present();
    }
}
