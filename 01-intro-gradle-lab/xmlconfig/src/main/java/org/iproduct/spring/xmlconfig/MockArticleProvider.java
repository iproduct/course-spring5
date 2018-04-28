package org.iproduct.spring.xmlconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

//@Component("provider")
public class MockArticleProvider implements ArticleProvider{
    public static MockArticleProvider createInstance(){
        return new MockArticleProvider();
    }

    @Autowired
    ApplicationContext ctx;

    @Value("${articles.title1}") String title1;
    @Value("${articles.content1}") String content1;

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
//                ctx.getBean("article", Article.class),
//                ctx.getBean("article", Article.class),
                new Article("Dependency Injection", "Should I use DI or lookup ..."),
                new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
        );
    }
}
