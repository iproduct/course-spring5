package course.spring.coredemo;

import course.spring.coredemo.domain.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfigDemoApp {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("app-config.xml");
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
