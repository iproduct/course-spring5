package course.spring.service.impl;

import course.spring.qualifiers.Alternative;
import course.spring.qualifiers.Mock;
import course.spring.service.ArticlePresenter;
import course.spring.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;


//@Service("presenter")
//@Scope("singleton")
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;


    public ConsoleArticlePresenter(ArticleProvider prov){
        this.provider = prov;
    }

//    public List<ArticleProvider> getProvider() {
//        return providers;
//    }
//
////    @Resource(name="mockProvider")
//    @Autowired
//    public void setProvider(List<ArticleProvider> providers) {
//        this.providers = providers;
//    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
//    @Override
//    public void present() {
//        providers.stream().flatMap(provider -> provider.getArticles().stream())
//                .forEach(System.out::println);
//    }
}
