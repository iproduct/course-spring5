package org.iproduct.spring.demo.beanconfig;

import org.iproduct.spring.demo.beanconfig.impl.ConsoleArticlePresenter;
import org.iproduct.spring.demo.beanconfig.impl.MockArticleProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class SpringXMLConfigDI {
    public static void main(String[] args) {
//        GenericApplicationContext ctx = new GenericApplicationContext();
//        ctx.registerBean("provider", MockArticleProvider.class);
//        ctx.registerBean("presenter", ConsoleArticlePresenter.class,
//                () -> new ConsoleArticlePresenter(
//                        ctx.getBean("provider", ArticleProvider.class)));
//        ctx.refresh();
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(SpringProgrammaticAnnotationConfig.class);
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
