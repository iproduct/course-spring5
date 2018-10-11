package org.iproduct.spring.xmlconfig;

public class ConsoleArticlePresenter implements  ArticlePresenter {
    private final ArticleProvider provider;

    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().stream().forEach(System.out::println);
    }
}
