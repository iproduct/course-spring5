package org.iproduct.spring.demo.beanconfig.service;

public interface ArticlePresenter {
    void present();
    ArticleProvider getArticleProvider();
    void setArticleProvider(ArticleProvider provider);
 }
