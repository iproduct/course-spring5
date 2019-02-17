package org.iproduct.spring.xmlconfig;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringXMLConfigAnnotationsDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new GenericXmlApplicationContext(
                "spring/app-context.xml");
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
