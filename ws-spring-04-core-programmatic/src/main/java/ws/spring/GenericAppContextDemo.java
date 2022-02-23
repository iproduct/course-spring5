package ws.spring;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import ws.spring.client.ArticlePresenter;
import ws.spring.client.impl.ConsoleArticlePresenter;
import ws.spring.dao.impl.ArticleRepositoryMemoryImpl;
import ws.spring.service.ArticleProvider;
import ws.spring.service.impl.MockArticleProvider;

public class GenericAppContextDemo {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean("mockRepository", ArticleRepositoryMemoryImpl.class);
        ctx.registerBean("provider", MockArticleProvider.class);
        ctx.registerBean("presenter", ConsoleArticlePresenter.class, () -> new ConsoleArticlePresenter(
                ctx.getBean(ArticleProvider.class)));
//        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
//        xmlReader.loadBeanDefinitions(new ClassPathResource("app-config.xml"));
        ctx.refresh();

        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
