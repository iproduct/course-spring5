package course.ws.client.impl;

import course.ws.client.ArticlePresenter;
import course.ws.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    @Autowired
    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
