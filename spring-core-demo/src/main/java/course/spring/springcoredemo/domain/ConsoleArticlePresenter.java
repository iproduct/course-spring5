package course.spring.springcoredemo.domain;

import course.spring.springcoredemo.dao.ArticlesRepository;

public class ConsoleArticlePresenter implements ArticlePresenter{
    private ArticlesRepository provider;

    public ConsoleArticlePresenter(ArticlesRepository provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.findAll().stream().forEach(System.out::println);
    }
}
