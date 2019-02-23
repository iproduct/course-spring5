package org.iproduct.spring.coredemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringXMLConfigDI {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "config/app-context.xml"
        );

        ArticlePresenter presenter =
                ctx.getBean("presenter", ArticlePresenter.class);

        presenter.present();
    }
}
