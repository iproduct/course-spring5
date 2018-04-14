package org.iproduct.spring.programmatic;

public interface ArticlePresenter {
    void present();
    ArticleProvider getArticleProvider();
    void setArticleProvider(ArticleProvider provider);
}
