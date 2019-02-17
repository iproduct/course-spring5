package org.iproduct.spring.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.aop.UsageTracking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("presenter")
//@Slf4j
public class ConsoleArticlePresenter implements ArticlePresenter {
    final static Logger log = LoggerFactory.getLogger(ConsoleArticlePresenter.class);

    @Value("#{provider}")
    private ArticleProvider provider;

    public ConsoleArticlePresenter() {
    }

    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        if (provider == null) {
            throw new RuntimeException(
                    "Error: Provider not set. Please set provider for articles before presenting them.");
        }
        provider.getArticles().forEach(article -> log.info(article.toString()));
    }

    @Override
    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public ArticleProvider getArticleProvider() {
        return provider;
    }
}
