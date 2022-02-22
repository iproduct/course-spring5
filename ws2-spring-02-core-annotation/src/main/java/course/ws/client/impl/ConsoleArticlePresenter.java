package course.ws.client.impl;

import course.ws.client.ArticlePresenter;
import course.ws.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    public ConsoleArticlePresenter(ArticleProvider repositoryArticleProvider) {
        this.provider = repositoryArticleProvider;
    }

    @Resource(name = "repositoryArticleProvider")
    public void setProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
