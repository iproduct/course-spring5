package course.ws.client.impl;

import course.ws.client.ArticlePresenter;
import course.ws.service.ArticleProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArticlePresenterFactory {
    private ArticleProvider provider;

    public void setProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    public ArticlePresenter createPresenter() {
        log.info("!!! Creating presenter using Presenter Factory.");
        return new ConsoleArticlePresenter(provider);
    }
}
