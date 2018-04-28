package org.iproduct.spring.xmlconfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    public ConsoleArticlePresenter() {}
    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        if(provider != null) {
            provider.getArticles().forEach(System.out::println);
        }
    }

    @Override
    public ArticleProvider getArticleProvider() {
        return provider;
    }

    @Override
//    @Autowired //@Inject //@Resource
    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }
}
