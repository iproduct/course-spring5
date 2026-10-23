package course.spring;

import course.spring.client.ArticlePresenter;
import course.spring.service.ArticleProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        var presenter = ctx.getBean(ArticlePresenter.class);
        presenter.presentArticles();
    }
}
