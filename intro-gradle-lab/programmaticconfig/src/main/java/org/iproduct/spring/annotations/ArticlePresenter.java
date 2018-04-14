package org.iproduct.spring.annotations;

public interface ArticlePresenter {
    void present();
    ArticleProvider getArticleProvider();
    void setArticleProvider(ArticleProvider provider);
}
