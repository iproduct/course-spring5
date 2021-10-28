package course.spring.service.impl;

import course.spring.service.ArticlePresenter;
import course.spring.service.ArticleProvider;

public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    public ConsoleArticlePresenter(ArticleProvider aProvider){
        this.provider = aProvider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
