package course.spring.springcoredemo;

import course.spring.springcoredemo.domain.ArticlePresenter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlContextDemo {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("app-config.xml");
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        System.out.println(presenter.getTitle());
        presenter.present();
    }
}
