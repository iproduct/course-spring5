package course.spring.coredemo.service.impl;

import course.spring.coredemo.dao.ArticleProvider;
import course.spring.coredemo.service.ArticlePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@Service("presenter")
@DependsOn("mockArticleProvider")
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    //    public ConsoleArticlePresenter(ArticleProvider provider) {
//        this.provider = provider;
//    }

    @Autowired // Dependency Injection pattern - property based DI
    public void setProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
