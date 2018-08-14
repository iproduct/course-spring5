package org.iproduct.spring.demo.beanconfig.impl;

import org.iproduct.spring.demo.beanconfig.Article;
import org.iproduct.spring.demo.beanconfig.ArticlePresenter;
import org.iproduct.spring.demo.beanconfig.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

//@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    public ConsoleArticlePresenter() {
    }

    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        if(provider == null) {
            throw new RuntimeException("ERROR: Provider not set.");
        }
        provider.getArticles().forEach(System.out::println);
    }

    @Override
    public ArticleProvider getArticleProvider() {
        return provider;
    }

    @Override
    @Autowired
    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }

}
