package course.spring.coredemo.consumer;

import course.spring.coredemo.model.Article;
import course.spring.coredemo.provider.ArticleProvider;

public class ConsoleArticleConsumer implements  ArticleConsumer{
    private ArticleProvider provider;

    public ConsoleArticleConsumer(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void consume() {
        provider.getAticles().forEach(System.out::println);
    }
}
