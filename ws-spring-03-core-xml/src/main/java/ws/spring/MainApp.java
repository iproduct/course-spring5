package ws.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import ws.spring.client.ArticlePresenter;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        ArticlePresenter presenter = ctx.getBean(ArticlePresenter.class);
        presenter.present();
    }
}
