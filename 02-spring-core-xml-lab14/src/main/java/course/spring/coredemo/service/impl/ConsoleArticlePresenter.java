package course.spring.coredemo.service.impl;

import course.spring.coredemo.dao.ArticleProvider;
import course.spring.coredemo.model.Author;
import course.spring.coredemo.service.ArticlePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;
    private Author author;

//    private ConsoleArticlePresenter(ArticleProvider provider) {
//        this.provider = provider;
//    }

//    @Resource(name = "provider")
//    public void setProvider(ArticleProvider provider) {
//        this.provider = provider;
//    }

    @Inject // @Autowired
    public void applyDependencies(ArticleProvider provider, Author author){
        this.provider = provider;
        this.author = author;
    }

    // factory method with arguments DI
//    public static ConsoleArticlePresenter createInstance(ArticleProvider provider) {
//        return new ConsoleArticlePresenter(provider);
//    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
        System.out.printf("Author: %s%n", author);
    }
}
