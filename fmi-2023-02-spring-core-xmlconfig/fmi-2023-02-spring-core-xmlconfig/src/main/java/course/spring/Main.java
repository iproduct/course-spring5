package course.spring;

import course.spring.presenter.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        // Lookup presenter using Service Locator pattern
        ArticlePresenter presenter = ctx.getBean(ArticlePresenter.class);
        presenter.present();
    }
}
