package org.iproduct.spring.beanconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

//@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
//    @Autowired
    private ArticleProvider articleProvider;

//    @Autowired
//    public ConsoleArticlePresenter(@Qualifier("alternativeProvider") ArticleProvider articleProvider) {
//        this.articleProvider = articleProvider;
//    }

    @Override
    public void present() {
        articleProvider.getArticles().forEach(System.out::println);
    }

    @Override
    public ArticleProvider getArticleProvider() {
        return articleProvider;
    }

    @Override
//    @Autowired
//    @Inject
//    @MockProvider
    public void setArticleProvider(ArticleProvider p) {
        this.articleProvider = p;
    }
}
