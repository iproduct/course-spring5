package org.iproduct.spring.spel;

public interface ArticlePresenter {
    void present();
    ArticleProvider getArticleProvider();
    void setArticleProvider(ArticleProvider provider);
 }
