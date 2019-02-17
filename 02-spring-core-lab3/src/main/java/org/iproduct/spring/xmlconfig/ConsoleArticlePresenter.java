package org.iproduct.spring.xmlconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component("presenter")
public class ConsoleArticlePresenter implements  ArticlePresenter {
    private final ArticleProvider provider;

//    @Autowired
    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().stream().forEach(System.out::println);
    }
}
