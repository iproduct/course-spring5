package org.iproduct.spring.coredemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Set;

//@Component("presenter")
//@PropertySource("articles.properties")
public class ConsoleArticlePresenter implements ArticlePresenter {
//    @Resource(name="provider")
//    @Autowired
    private ArticleProvider providers;
//    @Value("${message}")
    private String message = "Test message";

    public ConsoleArticlePresenter() {
    }

//    @Autowired
//    public ConsoleArticlePresenter(
//            ArticleProvider provider,
//            @Value("${message}") String message){
//        this.providers = provider;
//        this.message = message;
//    }

    public ArticleProvider getProviders() {
        return providers;
    }

    public void setProviders(ArticleProvider providers) {
        this.providers = providers;
    }

//    @Autowired
//    public void setProviderAndMessage(
//            ArticleProvider provider2,
//            @Value("${message}") String message) {
//        this.providers = provider2;
//        this.message = message;
//    }

    public String getMessage() {
        return message;
    }

//    @Value("${message}")
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void present() {
        System.out.println(message);
        if (providers != null)
//            providers.stream()
//                    .flatMap(provider -> provider.getArticles().stream())
//                    .forEach(System.out::println);
            providers.getArticles()
                .forEach(System.out::println);
        else {
            System.err.println("Article providers not set.");
        }
    }
}
