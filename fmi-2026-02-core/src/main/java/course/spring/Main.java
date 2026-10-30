package course.spring;

import course.spring.client.ArticlePresenter;
import course.spring.config.AppConfig;
import course.spring.service.ArticleProvider;
import course.spring.service.UsersProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        var presenter = ctx.getBean(ArticlePresenter.class);
        presenter.presentArticles();
        var usersProvider = ctx.getBean(UsersProvider.class);
        usersProvider.getUsers().forEach(System.out::println);
    }
}
