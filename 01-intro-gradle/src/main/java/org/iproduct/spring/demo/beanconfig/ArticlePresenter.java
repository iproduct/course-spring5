package org.iproduct.spring.demo.beanconfig;

public interface ArticlePresenter {
    void present();
    ArticleProvider getArticleProvider();
    void setArticleProvider(ArticleProvider provider);
 }
