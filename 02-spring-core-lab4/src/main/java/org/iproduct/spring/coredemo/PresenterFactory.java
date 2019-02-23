package org.iproduct.spring.coredemo;

public class PresenterFactory {
    private ArticleProvider provider;

    public PresenterFactory(ArticleProvider provider) {
        this.provider = provider;
    }

//    public ArticlePresenter createPresenter() {
//        return new ConsoleArticlePresenter(provider, "Factory: List of Articles:");
//    }
}
