package org.iproduct.spring.beanconfig;

public interface ArticlePresenter {
    void present();
    ArticleProvider getArticleProvider();
    void setArticleProvider(ArticleProvider provider);
}
