package course.spring.coredemo.domain;

import course.spring.coredemo.dao.ArticleRepository;

public class ArticlePresenterFactory {
    private ArticleRepository provider;
    public ArticlePresenterFactory(ArticleRepository provider) {
        this.provider = provider;
    }
    public ArticlePresenter creteArticlePresenter(){
        return new ConsoleArticlePresenter(provider);
    }
}
