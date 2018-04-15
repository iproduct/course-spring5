package org.iproduct.spring.demo.beanconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
    @Autowired //@Inject
    private ArticleProvider provider;

    public ConsoleArticlePresenter() {
    }

    //@Autowired // or @Inject
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

    @Override
//    @Resource //    @Autowired
    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public ArticleProvider getArticleProvider() {
        return provider;
    }
}
