package org.iproduct.spring.coredemo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Service("provider")
//@Primary
//@MockProvider
public class MockArticleProvider implements ArticleProvider {
    public static ArticleProvider createInstance(){
        return new MockArticleProvider();
    }

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
            new Article("Dependency Injection", "Should I use DI or lookup ..."),
            new Article("Spring AOP", "AOP is easy ..."),
            new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
        );
    }
}
