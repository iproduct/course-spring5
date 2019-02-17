package org.iproduct.spring.xmlconfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class ArticlePresenterFactory {
    private ArticleProvider provider;

    public ArticlePresenterFactory(ArticleProvider provider) {
        this.provider = provider;
    }

    public ArticlePresenter createPresenter() {
        return new ConsoleArticlePresenter(provider);
    }

}
