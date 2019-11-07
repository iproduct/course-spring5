package coredemo;

import coredemo.domain.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("coredemo");
        ArticlePresenter presenter = ctx.getBean(ArticlePresenter.class);
        presenter.present();
    }

}
