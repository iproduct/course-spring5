package org.iproduct.spring.programmatic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
    @Autowired
    @MockProvider
    private ArticleProvider provider;
    private String data;

//    @Autowired
//    @Inject
//    public ConsoleArticlePresenter(ArticleProvider provider) {
//        this.provider = provider;
//    }

//    public ConsoleArticlePresenter(ArticleProvider provider, String data) {
//        this.data = data;
//        this.provider = provider;
//    }

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
//    @Resource(name="provider")
//    @Autowired
//    @Inject
//    @AlternativeProvider
    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }
}
