package course.ws;

import course.ws.client.ArticlePresenter;
import course.ws.client.impl.ConsoleArticlePresenter;
import course.ws.config.AppConfig;
import course.ws.service.ArticleProvider;
import course.ws.service.UserService;
import course.ws.service.impl.MockArticleProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class GenericContextDemoMain {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean(MockArticleProvider.class);
        ctx.registerBean(ConsoleArticlePresenter.class, () -> {
            var presenter = new ConsoleArticlePresenter();
            presenter.setProvider(ctx.getBean(ArticleProvider.class));
            return presenter;
        });
        ctx.refresh();
        ArticlePresenter presenter = ctx.getBean(ArticlePresenter.class);
        presenter.present();
//        System.out.println();
//        UserService userService = ctx.getBean(UserService.class);
//        userService.getAllUsers().forEach(System.out::println);
    }
}
