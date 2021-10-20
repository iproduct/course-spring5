package course.spring.service.impl;

import course.spring.service.ArticlePresenter;
import course.spring.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    @Autowired
    public ConsoleArticlePresenter(ArticleProvider provider){
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
