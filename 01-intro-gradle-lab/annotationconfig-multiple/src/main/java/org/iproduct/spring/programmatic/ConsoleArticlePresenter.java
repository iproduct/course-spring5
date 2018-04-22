package org.iproduct.spring.programmatic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Set;

@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
    @Autowired
    private Set<ArticleProvider> providers;
    private String data;

//    @Autowired
//    @Inject
//    public ConsoleArticlePresenter(ArticleProvider provider) {
//        this.provider = provider;
//    }

//    public ConsoleArticlePresenter(ArticleProvider provider, String data) {
//        this.data = data;
//        this.provider = provider;
//    }

    @Override
    public void present() {
        if(providers != null) {

            providers.stream().map(p -> p.getArticles()).forEach(System.out::println);
        }
    }

//    @Override
    public Set<ArticleProvider> getArticleProvider() {
        return providers;
    }

//    @Override
////    @Resource(name="provider")
////    @Autowired
////    @Inject
////    @AlternativeProvider
//    public void setArticleProvider(ArticleProvider provider) {
//        this.provider = provider;
//    }
}
