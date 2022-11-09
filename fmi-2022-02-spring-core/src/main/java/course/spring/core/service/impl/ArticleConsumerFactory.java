package course.spring.core.service.impl;

import course.spring.core.service.ArticleProvider;
import course.spring.core.service.Consumer;

public class ArticleConsumerFactory {
    private ArticleProvider provider;

    public ArticleConsumerFactory(ArticleProvider provider) {
        this.provider = provider;
    }

    public Consumer createArticleConsumer() {
        return new ConsoleArticlePresenter(provider);
    }
}
