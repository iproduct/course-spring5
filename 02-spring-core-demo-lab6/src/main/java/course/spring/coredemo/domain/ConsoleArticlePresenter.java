package course.spring.coredemo.domain;

import course.spring.coredemo.dao.ArticleRepository;

public class ConsoleArticlePresenter implements ArticlePresenter{
    private ArticleRepository provider;
    private String title;

    public static ArticlePresenter createInstance(ArticleRepository provider) {
        return new ConsoleArticlePresenter(provider);
    }

    public ConsoleArticlePresenter() {}

    public ConsoleArticlePresenter(ArticleRepository provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        System.out.println("Title: " + title);
        provider.findAll().stream().forEach(System.out::println);
    }

    public ArticleRepository getProvider() {
        return provider;
    }

    public void setProvider(ArticleRepository provider) {
        this.provider = provider;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
