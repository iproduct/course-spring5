package org.iproduct.spring.demo.beanconfig;

import org.springframework.stereotype.Service;

@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
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
        provider.getArticles().forEach(System.out::println);
    }

    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    public ArticleProvider getArticleProvider() {
        return provider;
    }
}
