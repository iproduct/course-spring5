package org.iproduct.spring.xmlconfig;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

//@Service
public class MockArticleProvider implements ArticleProvider {
    public static MockArticleProvider createInstance(){
        return new MockArticleProvider();
    }

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
                new Article("Dependency Injection", "Should I use DI or lookup ..."),
                new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans."),
                new Article("Spring DI", "There are several ways to configure Spring DI.")
        );
    }

}
