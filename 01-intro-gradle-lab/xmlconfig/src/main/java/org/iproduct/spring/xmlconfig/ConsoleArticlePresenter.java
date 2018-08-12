package org.iproduct.spring.xmlconfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    public ConsoleArticlePresenter() {}
    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        if(provider != null) {
            provider.getArticles().forEach(System.out::println);
        } else {
            throw new NullPointerException("Provider should not be null!");
        }

    }

    @Override
    public ArticleProvider getArticleProvider() {
        return provider;
    }

    @Override
//    @Autowired // @Inject //@Resource
    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }
}
