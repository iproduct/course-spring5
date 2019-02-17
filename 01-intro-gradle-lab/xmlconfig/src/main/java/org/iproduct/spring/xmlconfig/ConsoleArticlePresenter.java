package org.iproduct.spring.xmlconfig;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {

    private ArticleProvider myProvider;

    public ConsoleArticlePresenter() {}
    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.myProvider = provider;
    }

    @Override
    public void present() {
        if(myProvider != null) {
            myProvider.getArticles().forEach(System.out::println);
        } else {
            throw new NullPointerException("Provider should not be null!");
        }

    }


    public ArticleProvider getArticleProvider() {
        return myProvider;
    }


//    @Autowired
// @Inject

    public void setArticleProvider(ArticleProvider provider) {
        this.myProvider = provider;
    }
}
