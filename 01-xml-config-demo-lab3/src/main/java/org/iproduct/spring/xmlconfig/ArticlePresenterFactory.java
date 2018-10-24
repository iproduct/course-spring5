package org.iproduct.spring.xmlconfig;


public class ArticlePresenterFactory {
    private ArticleProvider provider;

    public ArticlePresenterFactory(ArticleProvider provider) {
        this.provider = provider;
    }

    public ArticlePresenter createPresenter() {
        return new ConsoleArticlePresenter(provider);
    }

}
