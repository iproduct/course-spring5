package course.spring.coredemo.consumer;

import course.spring.coredemo.model.Article;
import course.spring.coredemo.provider.ArticleProvider;

public class ConsumerFactory {
    private ArticleProvider provider;

    public ConsumerFactory(ArticleProvider provider) {
        this.provider = provider;
    }

    public ArticleConsumer createConsumer() {
        provider.addArticle(new Article("Created by ConsumerFactory", "Created by ConsumerFactory"));
        return new ConsoleArticleConsumer(provider);
    }

}
