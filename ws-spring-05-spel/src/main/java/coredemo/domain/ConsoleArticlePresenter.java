package coredemo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
    @Autowired
    @Qualifier("alternativeProvider")
    private ArticleProvider provider;

    public ConsoleArticlePresenter() {
    }

//    public ConsoleArticlePresenter(ArticleProvider provider) {
//        this.provider = provider;
//    }

    @Override
    public void present() {
        provider.getArticles()
                .forEach(System.out::println);
    }

//    public static ArticlePresenter createPresenter(ArticleProvider prov) {
//        return new ConsoleArticlePresenter(prov);
//    }

//    public ArticleProvider getProvider() {
//        return provider;
//    }

//    @Autowired
//    @Inject
//    @Resource(name = "provider")
//    public void setProvider(ArticleProvider pr) {
//        this.provider = pr;
//    }

//    public  void configure(ArticleProvider pr) {
//        this.provider = pr;
//    }
}
