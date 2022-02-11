package ws.spring;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import ws.spring.client.ArticlePresenter;
import ws.spring.dao.UserRepository;
import ws.spring.service.UserService;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("ws.spring");
//        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
//        xmlReader.loadBeanDefinitions(new ClassPathResource("app-config.xml"));
//        ctx.refresh();
        ArticlePresenter presenter = ctx.getBean(ArticlePresenter.class);
        presenter.present();
        UserService userService = ctx.getBean(UserService.class);
        System.out.println("\nUsers:");
        userService.getUsers().forEach(System.out::println);
    }
}
