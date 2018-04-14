package org.iproduct.spring.programmatic;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;

@ComponentScan(basePackageClasses = org.iproduct.spring.programmatic.SpringAnnotationsDI.class)
public class SpringAnnotationsDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new GenericApplicationContext();
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
