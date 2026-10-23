package course.spring.client.impl;

import course.spring.client.ArticlePresenter;
import course.spring.model.Article;
import course.spring.service.ArticleProvider;

import java.util.List;

public class ArticlePreseterImpl implements ArticlePresenter {
    private ArticleProvider provider;

    public ArticlePreseterImpl() {
    }

    public ArticlePreseterImpl(ArticleProvider provider) {
        this.provider = provider;
    }

    public void setProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void presentArticles() {
        provider.getArticles().forEach(System.out::println);
    }
}
