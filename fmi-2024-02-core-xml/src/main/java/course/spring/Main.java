package course.spring;

import course.spring.domain.PostProvider;
import course.spring.model.Post;
import course.spring.presenter.PostPresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        // Lookup using Service Locator pattern
        PostPresenter presenter = ctx.getBean("presenter", PostPresenter.class);
        presenter.present();
    }
}
