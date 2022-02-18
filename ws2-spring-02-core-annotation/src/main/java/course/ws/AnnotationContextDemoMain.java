package course.ws;

import course.ws.client.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationContextDemoMain {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("course.ws");
        ArticlePresenter presenter = ctx.getBean(ArticlePresenter.class);
        presenter.present();
    }
}
