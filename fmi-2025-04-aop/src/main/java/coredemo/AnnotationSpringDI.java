package coredemo;

import coredemo.domain.ArticlePresenter;
import coredemo.domain.ArticleProvider;
import coredemo.model.Article;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationSpringDI {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
        System.out.println();
        ArticleProvider provider = ctx.getBean("provider", ArticleProvider.class);
        provider.addArticle(new Article("New Article 1", "New Content 1..."));
        provider.addArticle(new Article("New Post 06.Jan.2021", "New Content 06.Jan.2021 ..."));
        presenter.present();
    }
}
