package org.iproduct.spring.programmatic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    public ConsoleArticlePresenter() {
    }

    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        if(provider != null) {
            provider.getArticles().forEach(System.out::println);
        }
    }

    @Override
    public ArticleProvider getArticleProvider() {
        return provider;
    }

    @Override
    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }
}
