package org.iproduct.spring.programmatic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

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
//    @Resource
    @Autowired
    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }
}
