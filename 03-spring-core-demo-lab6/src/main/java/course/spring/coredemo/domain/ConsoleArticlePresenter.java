package course.spring.coredemo.domain;

import course.spring.coredemo.dao.ArticleRepository;

public class ConsoleArticlePresenter implements ArticlePresenter{
    private ArticleRepository provider;

    public ConsoleArticlePresenter(ArticleRepository provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.findAll().stream().forEach(System.out::println);
    }
}
