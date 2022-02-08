package ws.spring.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ws.spring.client.ArticlePresenter;
import ws.spring.service.ArticleProvider;

import javax.annotation.Resource;

@Component
public class ConsoleArticlePresenter implements ArticlePresenter {
    @Resource(name = "repoProvider")
    private ArticleProvider provider;

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
