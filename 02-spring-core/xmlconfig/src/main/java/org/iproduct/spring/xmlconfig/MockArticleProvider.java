package org.iproduct.spring.xmlconfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("provider")
public class MockArticleProvider implements ArticleProvider,
        InitializingBean, DisposableBean, ApplicationContextAware {
    public static MockArticleProvider createInstance(){
        return new MockArticleProvider();
    }

//    @Autowired
    ApplicationContext ctx;

//    @Value("${articles.title1}") String title1;
//    @Value("${articles.content1}") String content1;

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
//                ctx.getBean("article", Article.class),
//                ctx.getBean("article", Article.class),
//                new Article(title1, content1),
                new Article("Dependency Injection", "Should I use DI or lookup ..."),
                new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
        );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MockArticleProvider initialized");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("MockArticleProvider to be destroyed");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
