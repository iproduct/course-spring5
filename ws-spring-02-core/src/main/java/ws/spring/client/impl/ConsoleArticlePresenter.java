package ws.spring.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ws.spring.client.ArticlePresenter;
import ws.spring.service.ArticleProvider;
import ws.spring.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ConsoleArticlePresenter implements ArticlePresenter {
    @Autowired
//    @Qualifier("repoProvider")
    private ArticleProvider provider;
    @Autowired
    private UserService userService;
//    @Autowired
//    private List<ArticleProvider> providers;

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
