package course.ws;

import course.ws.client.ArticlePresenter;
import course.ws.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationContextDemoMain {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        ArticlePresenter presenter = ctx.getBean(ArticlePresenter.class);
        presenter.present();
//        System.out.println();
//        UserService userService = ctx.getBean(UserService.class);
//        userService.getAllUsers().forEach(System.out::println);
    }
}
