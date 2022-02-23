package course.ws.client.impl;

import course.ws.client.ArticlePresenter;
import course.ws.qualifiers.Default;
import course.ws.qualifiers.Mock;
import course.ws.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.List;

//@Component
public class ConsoleArticlePresenter implements ArticlePresenter {
//    private List<ArticleProvider> providers;
    private ArticleProvider provider;

//    @Inject
//    public ConsoleArticlePresenter(ArticleProvider repositoryArticleProvider) {
//        this.provider = repositoryArticleProvider;
//    }

//    @Inject
    public void setProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
//    @Override
//    public void present() {
//        providers.stream().flatMap(p -> p.getArticles().stream())
//                .forEach(System.out::println);
//    }
}
