package course.spring.coredemo;

import course.spring.coredemo.config.AppConfig;
import course.spring.coredemo.consumer.ArticleConsumer;
import course.spring.coredemo.consumer.ConsoleArticleConsumer;
import course.spring.coredemo.formatter.ArticleFormatter;
import course.spring.coredemo.formatter.DefaultArticleFormatter;
import course.spring.coredemo.provider.ArticleProvider;
import course.spring.coredemo.provider.MockArticleProvider;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class GenericAppContextDemo {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean("formatter", DefaultArticleFormatter.class);
        ctx.registerBean("provider", MockArticleProvider.class);
        ctx.registerBean("consumer", ConsoleArticleConsumer.class, () -> new ConsoleArticleConsumer(
                ctx.getBean(ArticleProvider.class), ctx.getBean(ArticleFormatter.class)));
//        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
//        xmlReader.loadBeanDefinitions(new ClassPathResource("app-config.xml"));
        ctx.refresh();

        ArticleConsumer consumer = ctx.getBean("consumer", ArticleConsumer.class);
        consumer.consume();
    }
}
