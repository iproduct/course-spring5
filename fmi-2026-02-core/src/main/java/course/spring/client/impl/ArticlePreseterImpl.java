package course.spring.client.impl;

import course.spring.client.ArticlePresenter;
import course.spring.model.Article;
import course.spring.service.ArticleProvider;

import java.util.List;

public class ArticlePreseterImpl implements ArticlePresenter {
    private List<Article> articles;

    public ArticlePreseterImpl(List<Article> articles) {
        this.articles = articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public void presentArticles() {
        articles.forEach(System.out::println);
    }
}
