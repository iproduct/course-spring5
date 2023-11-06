package course.spring.presenter.impl;

import course.spring.model.Article;
import course.spring.presenter.ArticlePresenter;
import course.spring.provider.ArticleProvider;

import java.util.List;

public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
