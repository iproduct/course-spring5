package org.iproduct.spring.demo.beanconfig;

import org.iproduct.spring.demo.beanconfig.ArticleProvider;

public interface ArticlePresenter {
    void present();
    ArticleProvider getArticleProvider();
    void setArticleProvider(ArticleProvider provider);
}
