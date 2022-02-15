package ws.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import ws.spring.client.ArticlePresenter;
import ws.spring.config.ApplicationConfig;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
