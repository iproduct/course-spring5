package course.spring.client.impl;

import course.spring.client.ArticlePresenter;
import course.spring.model.Article;
import course.spring.service.ArticleProvider;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ArticlePreseterImpl implements ArticlePresenter {
    private ArticleProvider[] provider;

    public ArticlePreseterImpl() {
    }

//    public ArticlePreseterImpl(ArticleProvider provider) {
//        this.provider = provider;
//    }

//    @Resource(name="articleProviderFromProperties")
    @Autowired
//    @Qualifier("articleProviderInMemory")
//    @Qualifier("IN_MEMORY")
//    public void setProvider(ArticleProvider provider) {
//        this.provider = provider;
//    }
    public void setProvider(ArticleProvider[] provider) {
        this.provider = provider;
    }

    @Override
//    public void presentArticles() {
//        provider.getArticles().forEach(System.out::println);
//    }
    public void presentArticles() {
        Arrays.stream(provider).flatMap(p -> p.getArticles().stream())
                .forEach(System.out::println);
    }
}
