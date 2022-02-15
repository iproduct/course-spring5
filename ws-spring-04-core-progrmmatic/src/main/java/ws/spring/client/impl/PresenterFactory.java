package ws.spring.client.impl;

import lombok.extern.slf4j.Slf4j;
import ws.spring.client.ArticlePresenter;
import ws.spring.service.ArticleProvider;

@Slf4j
public class PresenterFactory {
    private ArticleProvider provider;

    public PresenterFactory(ArticleProvider provider) {
        this.provider = provider;
    }

    public ArticlePresenter createPresenter() {
        log.info("\"Creating ConsoleArticlePresenter using PresenterFactory class.\"");
        return new ConsoleArticlePresenter(provider);
    }
}
