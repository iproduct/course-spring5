package ws.spring.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ws.spring.client.ArticlePresenter;
import ws.spring.service.ArticleProvider;

import javax.annotation.Resource;

//@Component
@Slf4j
public class ConsoleArticlePresenter implements ArticlePresenter {
    public static ArticlePresenter createArticlePresenter(ArticleProvider provider) {
        Logger log = LoggerFactory.getLogger(ConsoleArticlePresenter.class);
        log.info("Creating ConsoleArticlePresenter using Factory Method.");
        return new ConsoleArticlePresenter(provider);
    }

    private ArticleProvider provider;

    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
