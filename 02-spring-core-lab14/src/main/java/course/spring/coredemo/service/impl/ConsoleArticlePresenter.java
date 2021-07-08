package course.spring.coredemo.service.impl;

import course.spring.coredemo.dao.ArticleProvider;
import course.spring.coredemo.service.ArticlePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("presenter")
//@DependsOn("mockArticleProvider")
//@Scope("prototype")
public class ConsoleArticlePresenter implements ArticlePresenter {
    private List<ArticleProvider> providers;

    @Autowired
    public ConsoleArticlePresenter(List<ArticleProvider> provider) {
        this.providers = provider;
    }

//    @Autowired // Dependency Injection pattern - property based DI
//    public void setProvider(ArticleProvider provider) {
//        this.provider = provider;
//    }

    @Override
    public void present() {
        providers.stream().flatMap(p -> p.getArticles().stream()).forEach(System.out::println);
    }
}
